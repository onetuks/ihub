package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.dto.communication.CommentCreateRequest;
import com.onetuks.ihub.dto.communication.CommentUpdateRequest;
import com.onetuks.ihub.dto.communication.MentionCreateRequest;
import com.onetuks.ihub.entity.communication.Comment;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.entity.communication.Mention;
import com.onetuks.ihub.entity.communication.Post;
import com.onetuks.ihub.entity.communication.TargetType;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.task.Task;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.CommentMapper;
import com.onetuks.ihub.mapper.MentionMapper;
import com.onetuks.ihub.repository.CommentJpaRepository;
import com.onetuks.ihub.repository.EventJpaRepository;
import com.onetuks.ihub.repository.MentionJpaRepository;
import com.onetuks.ihub.repository.PostJpaRepository;
import com.onetuks.ihub.repository.TaskJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.project.ProjectMemberCheckComponent;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;
  private final CommentJpaRepository commentJpaRepository;
  private final PostJpaRepository postJpaRepository;
  private final EventJpaRepository eventJpaRepository;
  private final TaskJpaRepository taskJpaRepository;
  private final MentionJpaRepository mentionJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public CommentCreateResult create(
      User currentUser,
      TargetType targetType,
      String targetId,
      CommentCreateRequest request
  ) {
    Project project = resolveProject(targetType, targetId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser.getUserId(),
        project.getProjectId());

    Comment parentComment = null;
    if (request.parentCommentId() != null) {
      parentComment = findParentComment(request.parentCommentId(), project, targetType, targetId);
    }

    Comment comment = CommentMapper.applyCreate(currentUser, project, targetType, targetId,
        request);
    comment.setParentComment(parentComment);

    Comment result = commentJpaRepository.save(comment);
    List<Mention> mentions =
        createMentions(currentUser, project, targetType, targetId, request.mentions());
    return new CommentCreateResult(result, mentions);
  }

  @Transactional(readOnly = true)
  public List<Comment> getAll(User currentUser, TargetType targetType, String targetId) {
    Project project = resolveProject(targetType, targetId);
    projectMemberCheckComponent.checkIsProjectViewer(currentUser.getUserId(),
        project.getProjectId());
    return commentJpaRepository.findAllByTargetTypeAndTargetIdOrderByCreatedAtAsc(targetType,
        targetId);
  }

  @Transactional(readOnly = true)
  public Comment getById(User currentUser, String commentId) {
    Comment comment = findEntity(commentId);
    projectMemberCheckComponent.checkIsProjectViewer(
        currentUser.getUserId(), comment.getProject().getProjectId());
    return comment;
  }

  @Transactional
  public Comment update(User currentUser, String commentId, CommentUpdateRequest request) {
    Comment comment = findEntity(commentId);
    projectMemberCheckComponent.checkIsProjectMember(
        currentUser.getUserId(), comment.getProject().getProjectId());
    CommentMapper.applyUpdate(comment, request);
    return comment;
  }

  @Transactional
  public void delete(User currentUser, String commentId) {
    Comment comment = findEntity(commentId);
    projectMemberCheckComponent.checkIsProjectMember(
        currentUser.getUserId(), comment.getProject().getProjectId());
    commentJpaRepository.delete(comment);
  }

  private Comment findEntity(String commentId) {
    return commentJpaRepository.findById(commentId)
        .orElseThrow(() -> new EntityNotFoundException("Comment not found: " + commentId));
  }

  private Comment findParentComment(
      String parentCommentId,
      Project project,
      TargetType targetType,
      String targetId
  ) {
    Comment parent = findEntity(parentCommentId);
    if (!Objects.equals(parent.getProject().getProjectId(), project.getProjectId())
        || parent.getTargetType() != targetType
        || !Objects.equals(parent.getTargetId(), targetId)) {
      throw new IllegalArgumentException("Parent comment target does not match.");
    }
    return parent;
  }

  private Project resolveProject(TargetType targetType, String targetId) {
    return switch (targetType) {
      case POST -> findPost(targetId).getProject();
      case EVENT -> findEvent(targetId).getProject();
      case TASK -> findTask(targetId).getProject();
    };
  }

  private Post findPost(String postId) {
    return postJpaRepository.findById(postId)
        .orElseThrow(() -> new EntityNotFoundException("Post not found: " + postId));
  }

  private Event findEvent(String eventId) {
    return eventJpaRepository.findById(eventId)
        .orElseThrow(() -> new EntityNotFoundException("Event not found: " + eventId));
  }

  private Task findTask(String taskId) {
    return taskJpaRepository.findById(taskId)
        .orElseThrow(() -> new EntityNotFoundException("Task not found: " + taskId));
  }

  private User findUser(String userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }

  private List<Mention> createMentions(
      User currentUser,
      Project project,
      TargetType targetType,
      String targetId,
      List<String> mentionedUserIds
  ) {
    List<String> mentionTargets = mentionedUserIds == null ? List.of() : mentionedUserIds;
    if (mentionTargets.isEmpty()) {
      return Collections.emptyList();
    }

    List<Mention> mentions = mentionTargets.stream()
        .map(mentionedUserId ->
            buildMention(currentUser, project, targetType, targetId, mentionedUserId))
        .toList();
    return mentionJpaRepository.saveAll(mentions);
  }

  private Mention buildMention(
      User currentUser,
      Project project,
      TargetType targetType,
      String targetId,
      String mentionedUserId
  ) {
    Mention mention = new Mention();
    MentionMapper.applyCreate(mention, new MentionCreateRequest(
        project.getProjectId(),
        targetType,
        targetId,
        mentionedUserId,
        currentUser.getUserId()
    ));
    mention.setProject(project);
    mention.setMentionedUser(findUser(mentionedUserId));
    mention.setCreatedBy(currentUser);
    return mention;
  }

  public record CommentCreateResult(Comment comment, List<Mention> mentions) {

  }
}

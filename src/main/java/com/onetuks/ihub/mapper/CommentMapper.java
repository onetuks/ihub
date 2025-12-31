package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.communication.CommentCreateRequest;
import com.onetuks.ihub.dto.communication.CommentResponse;
import com.onetuks.ihub.dto.communication.CommentUpdateRequest;
import com.onetuks.ihub.entity.communication.Comment;
import com.onetuks.ihub.entity.communication.TargetType;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;

public final class CommentMapper {

  private CommentMapper() {
  }

  public static CommentResponse toResponse(Comment comment) {
    return new CommentResponse(
        comment.getCommentId(),
        comment.getProject() != null ? comment.getProject().getProjectId() : null,
        comment.getParentComment() != null ? comment.getParentComment().getCommentId() : null,
        comment.getTargetType(),
        comment.getTargetId(),
        comment.getContent(),
        comment.getCreatedBy() != null ? comment.getCreatedBy().getUserId() : null,
        comment.getCreatedAt(),
        comment.getUpdatedAt());
  }

  public static Comment applyCreate(
      User currentUser,
      Project project,
      TargetType targetType,
      String targetId,
      CommentCreateRequest request
  ) {
    LocalDateTime now = LocalDateTime.now();
    Comment comment = new Comment();
    comment.setCommentId(UUIDProvider.provideUUID(Comment.TABLE_NAME));
    comment.setProject(project);
    comment.setTargetType(targetType);
    comment.setTargetId(targetId);
    comment.setContent(request.content());
    comment.setCreatedBy(currentUser);
    comment.setCreatedAt(now);
    comment.setUpdatedAt(now);
    return comment;
  }

  public static void applyUpdate(Comment comment, CommentUpdateRequest request) {
    comment.setContent(request.content());
    comment.setUpdatedAt(LocalDateTime.now());
  }
}

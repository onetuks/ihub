package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.CommentCreateRequest;
import com.onetuks.ihub.dto.communication.CommentCreateResponse;
import com.onetuks.ihub.dto.communication.CommentResponse;
import com.onetuks.ihub.dto.communication.CommentUpdateRequest;
import com.onetuks.ihub.entity.communication.TargetType;
import com.onetuks.ihub.mapper.CommentMapper;
import com.onetuks.ihub.mapper.MentionMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.communication.CommentService;
import com.onetuks.ihub.service.communication.CommentService.CommentCreateResult;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentRestControllerImpl implements CommentRestController {

  private final CurrentUserProvider currentUserProvider;
  private final CommentService commentService;

  @Override
  public ResponseEntity<CommentCreateResponse> createComment(
      @PathVariable TargetType targetType,
      @PathVariable String targetId,
      @Valid @RequestBody CommentCreateRequest request
  ) {
    CommentCreateResult result = commentService.create(
        currentUserProvider.resolveUser(),
        targetType,
        targetId,
        request
    );
    CommentCreateResponse response = new CommentCreateResponse(
        CommentMapper.toResponse(result.comment()),
        result.mentions().stream().map(MentionMapper::toResponse).toList()
    );
    return ResponseEntity.created(URI.create("/api/comments/" + response.comment().commentId()))
        .body(response);
  }

  @Override
  public ResponseEntity<List<CommentResponse>> getComments(
      @PathVariable TargetType targetType,
      @PathVariable String targetId
  ) {
    List<CommentResponse> responses = commentService.getAll(
            currentUserProvider.resolveUser(),
            targetType,
            targetId
        ).stream()
        .map(CommentMapper::toResponse)
        .toList();
    return ResponseEntity.ok(responses);
  }

  @Override
  public ResponseEntity<CommentResponse> updateComment(
      @PathVariable String commentId,
      @Valid @RequestBody CommentUpdateRequest request
  ) {
    CommentResponse response = CommentMapper.toResponse(
        commentService.update(currentUserProvider.resolveUser(), commentId, request)
    );
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> deleteComment(@PathVariable String commentId) {
    commentService.delete(currentUserProvider.resolveUser(), commentId);
    return ResponseEntity.noContent().build();
  }
}

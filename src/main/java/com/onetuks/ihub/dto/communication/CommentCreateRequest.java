package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import jakarta.validation.constraints.NotNull;

public record CommentCreateRequest(
    @NotNull String projectId,
    String parentCommentId,
    TargetType targetType,
    String targetId,
    String content,
    String createdById
) {
}

package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import jakarta.validation.constraints.NotNull;

public record MentionCreateRequest(
    @NotNull String projectId,
    @NotNull TargetType targetType,
    @NotNull String targetId,
    @NotNull String mentionedUserId,
    @NotNull String createdById
) {
}

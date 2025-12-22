package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.communication.TargetType;
import jakarta.validation.constraints.NotNull;

public record AttachmentCreateRequest(
    @NotNull String projectId,
    @NotNull String fileId,
    @NotNull TargetType targetType,
    @NotNull String targetId,
    @NotNull String attachedById
) {
}

package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.interfaces.InterfaceRole;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusTransitionStatus;
import jakarta.validation.constraints.NotNull;

public record InterfaceStatusTransitionCreateRequest(
    @NotNull String projectId,
    @NotNull String fromStatusId,
    @NotNull String toStatusId,
    @NotNull InterfaceRole allowedRole,
    @NotNull InterfaceStatusTransitionStatus status,
    @NotNull String createdById
) {
}

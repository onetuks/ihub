package com.onetuks.ihub.dto.interfaces;

import jakarta.validation.constraints.NotNull;

public record InterfaceStatusHistoryCreateRequest(
    @NotNull String interfaceId,
    @NotNull String fromStatusId,
    @NotNull String toStatusId,
    @NotNull String changedById,
    @NotNull String relatedTaskId,
    String reason
) {

}

package com.onetuks.ihub.dto.interfaces;

public record InterfaceStatusHistoryUpdateRequest(
    String toStatusId,
    String reason
) {
}

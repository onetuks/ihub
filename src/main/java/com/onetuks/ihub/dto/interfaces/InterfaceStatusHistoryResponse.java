package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record InterfaceStatusHistoryResponse(
    String historyId,
    String interfaceId,
    String fromStatusId,
    String toStatusId,
    String changedById,
    UserStatus changedByStatus,
    String changedByName,
    LocalDateTime changedAt,
    String relatedTaskId,
    String reason
) {

}

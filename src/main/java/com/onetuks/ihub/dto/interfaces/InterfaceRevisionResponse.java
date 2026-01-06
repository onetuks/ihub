package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;
import java.util.Map;

public record InterfaceRevisionResponse(
    String revisionId,
    String interfaceId,
    Integer versionNo,
    String changedById,
    UserStatus changedByStatus,
    String changedByName,
    LocalDateTime changedAt,
    Map<String, String> snapshot,
    String reason
) {

}

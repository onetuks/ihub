package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.interfaces.InterfaceRole;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusTransitionStatus;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record InterfaceStatusTransitionResponse(
    String transitionId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String fromStatusId,
    String toStatusId,
    InterfaceRole allowedRole,
    InterfaceStatusTransitionStatus status,
    LocalDateTime createdAt,
    String createdById,
    UserStatus createdByStatus,
    String createdByName
) {

}

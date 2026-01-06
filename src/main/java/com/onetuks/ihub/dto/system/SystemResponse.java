package com.onetuks.ihub.dto.system;

import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.system.SystemEnvironment;
import com.onetuks.ihub.entity.system.SystemStatus;
import com.onetuks.ihub.entity.system.SystemType;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record SystemResponse(
    String systemId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String systemCode,
    SystemStatus status,
    String description,
    SystemType systemType,
    SystemEnvironment environment,
    String createdById,
    UserStatus createdByStatus,
    String createdByName,
    String updatedById,
    UserStatus updatedByStatus,
    String updatedByName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}

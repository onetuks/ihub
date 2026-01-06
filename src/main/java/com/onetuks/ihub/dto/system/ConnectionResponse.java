package com.onetuks.ihub.dto.system;

import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.system.ConnectionStatus;
import com.onetuks.ihub.entity.system.Protocol;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record ConnectionResponse(
    String connectionId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String systemId,
    String name,
    Protocol protocol,
    String host,
    Integer port,
    String path,
    String username,
    String authType,
    String extraConfig,
    ConnectionStatus status,
    String description,
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

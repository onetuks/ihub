package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.system.SystemCreateRequest;
import com.onetuks.ihub.dto.system.SystemResponse;
import com.onetuks.ihub.dto.system.SystemUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.system.System;
import com.onetuks.ihub.entity.system.SystemStatus;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;
import java.util.Objects;

public final class SystemMapper {

  private SystemMapper() {
  }

  public static SystemResponse toResponse(System system) {
    var project = Objects.requireNonNull(system.getProject());
    var createdBy = Objects.requireNonNull(system.getCreatedBy());
    var updatedBy = Objects.requireNonNull(system.getUpdatedBy());
    return new SystemResponse(
        system.getSystemId(),
        project.getProjectId(),
        project.getStatus(),
        project.getTitle(),
        system.getSystemCode(),
        system.getStatus(),
        system.getDescription(),
        system.getSystemType(),
        system.getEnvironment(),
        createdBy.getUserId(),
        createdBy.getStatus(),
        createdBy.getName(),
        updatedBy.getUserId(),
        updatedBy.getStatus(),
        updatedBy.getName(),
        system.getCreatedAt(),
        system.getUpdatedAt());
  }

  public static System applyCreate(Project project, User currentUser, SystemCreateRequest request) {
    LocalDateTime now = LocalDateTime.now();
    return new System(
        UUIDProvider.provideUUID(System.TABLE_NAME),
        project,
        request.systemCode(),
        SystemStatus.ACTIVE,
        request.description(),
        request.systemType(),
        request.environment(),
        currentUser,
        currentUser,
        now,
        now
    );
  }

  public static System applyUpdate(System system, User currentUser, SystemUpdateRequest request) {
    if (request.systemCode() != null) {
      system.setSystemCode(request.systemCode());
    }
    if (request.status() != null) {
      system.setStatus(request.status());
    }
    if (request.description() != null) {
      system.setDescription(request.description());
    }
    if (request.systemType() != null) {
      system.setSystemType(request.systemType());
    }
    if (request.environment() != null) {
      system.setEnvironment(request.environment());
    }
    system.setUpdatedBy(currentUser);
    system.setUpdatedAt(LocalDateTime.now());
    return system;
  }
}

package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusUpdateRequest;
import com.onetuks.ihub.entity.interfaces.InterfaceStatus;
import com.onetuks.ihub.entity.project.Project;
import java.time.LocalDateTime;
import java.util.Objects;

public final class InterfaceStatusMapper {

  private InterfaceStatusMapper() {
  }

  public static InterfaceStatusResponse toResponse(InterfaceStatus status) {
    var project = Objects.requireNonNull(status.getProject());
    return new InterfaceStatusResponse(
        status.getStatusId(),
        project.getProjectId(),
        project.getStatus(),
        project.getTitle(),
        status.getName(),
        status.getCode(),
        status.getSeqOrder(),
        status.getIsDefault(),
        status.getCreatedAt(),
        status.getUpdatedAt());
  }

  public static InterfaceStatus applyCreate(Project project, InterfaceStatusCreateRequest request) {
    LocalDateTime now = LocalDateTime.now();
    return new InterfaceStatus(
        UUIDProvider.provideUUID(InterfaceStatus.TABLE_NAME),
        project,
        request.name(),
        request.code(),
        request.seqOrder(),
        request.isDefault(),
        now,
        now
    );
  }

  public static InterfaceStatus applyUpdate(InterfaceStatus status,
      InterfaceStatusUpdateRequest request) {
    if (request.name() != null) {
      status.setName(request.name());
    }
    if (request.code() != null) {
      status.setCode(request.code());
    }
    if (request.seqOrder() != null) {
      status.setSeqOrder(request.seqOrder());
    }
    if (request.isDefault() != null) {
      status.setIsDefault(request.isDefault());
    }
    status.setUpdatedAt(LocalDateTime.now());
    return status;
  }
}

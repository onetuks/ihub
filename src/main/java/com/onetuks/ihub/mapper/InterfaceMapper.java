package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.interfaces.InterfaceCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceUpdateRequest;
import com.onetuks.ihub.entity.interfaces.Interface;
import java.time.LocalDateTime;
import java.util.Objects;

public final class InterfaceMapper {

  private InterfaceMapper() {
  }

  public static InterfaceResponse toResponse(Interface anInterface) {
    var project = Objects.requireNonNull(anInterface.getProject());
    var sourceSystem = Objects.requireNonNull(anInterface.getSourceSystem());
    var targetSystem = Objects.requireNonNull(anInterface.getTargetSystem());
    var status = Objects.requireNonNull(anInterface.getStatus());
    var createdBy = Objects.requireNonNull(anInterface.getCreatedBy());
    return new InterfaceResponse(
        anInterface.getInterfaceId(),
        project.getProjectId(),
        project.getStatus(),
        project.getTitle(),
        anInterface.getIfId(),
        sourceSystem.getSystemId(),
        targetSystem.getSystemId(),
        anInterface.getModule(),
        anInterface.getInterfaceType(),
        anInterface.getPattern(),
        anInterface.getSenderAdapter(),
        anInterface.getReceiverAdapter(),
        anInterface.getSa(),
        status.getStatusId(),
        anInterface.getBatchTimeLabel(),
        anInterface.getRemark(),
        createdBy.getUserId(),
        createdBy.getStatus(),
        createdBy.getName(),
        anInterface.getCreatedAt(),
        anInterface.getUpdatedAt());
  }

  public static void applyCreate(Interface anInterface, InterfaceCreateRequest request) {
    LocalDateTime now = LocalDateTime.now();
    anInterface.setInterfaceId(UUIDProvider.provideUUID(Interface.TABLE_NAME));
    anInterface.setIfId(request.ifId());
    anInterface.setModule(request.module());
    anInterface.setInterfaceType(request.interfaceType());
    anInterface.setPattern(request.pattern());
    anInterface.setSenderAdapter(request.senderAdapter());
    anInterface.setReceiverAdapter(request.receiverAdapter());
    anInterface.setSa(request.sa());
    anInterface.setBatchTimeLabel(request.batchTimeLabel());
    anInterface.setRemark(request.remark());
    anInterface.setCreatedAt(now);
    anInterface.setUpdatedAt(now);
  }

  public static void applyUpdate(Interface anInterface, InterfaceUpdateRequest request) {
    if (request.ifId() != null) {
      anInterface.setIfId(request.ifId());
    }
    if (request.module() != null) {
      anInterface.setModule(request.module());
    }
    if (request.interfaceType() != null) {
      anInterface.setInterfaceType(request.interfaceType());
    }
    if (request.pattern() != null) {
      anInterface.setPattern(request.pattern());
    }
    if (request.senderAdapter() != null) {
      anInterface.setSenderAdapter(request.senderAdapter());
    }
    if (request.receiverAdapter() != null) {
      anInterface.setReceiverAdapter(request.receiverAdapter());
    }
    if (request.sa() != null) {
      anInterface.setSa(request.sa());
    }
    if (request.batchTimeLabel() != null) {
      anInterface.setBatchTimeLabel(request.batchTimeLabel());
    }
    if (request.remark() != null) {
      anInterface.setRemark(request.remark());
    }
    anInterface.setUpdatedAt(LocalDateTime.now());
  }
}

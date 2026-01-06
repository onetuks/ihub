package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusHistoryCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusHistoryResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusHistoryUpdateRequest;
import com.onetuks.ihub.entity.interfaces.InterfaceStatusHistory;
import java.time.LocalDateTime;
import java.util.Objects;

public final class InterfaceStatusHistoryMapper {

  private InterfaceStatusHistoryMapper() {
  }

  public static InterfaceStatusHistoryResponse toResponse(InterfaceStatusHistory history) {
    var anInterface = Objects.requireNonNull(history.getAnInterface());
    var fromStatus = Objects.requireNonNull(history.getFromStatus());
    var toStatus = Objects.requireNonNull(history.getToStatus());
    var changedBy = Objects.requireNonNull(history.getChangedBy());
    var relatedTask = Objects.requireNonNull(history.getRelatedTask());
    return new InterfaceStatusHistoryResponse(
        history.getHistoryId(),
        anInterface.getInterfaceId(),
        fromStatus.getStatusId(),
        toStatus.getStatusId(),
        changedBy.getUserId(),
        changedBy.getStatus(),
        changedBy.getName(),
        history.getChangedAt(),
        relatedTask.getTaskId(),
        history.getReason());
  }

  public static void applyCreate(InterfaceStatusHistory history,
      InterfaceStatusHistoryCreateRequest request) {
    history.setHistoryId(UUIDProvider.provideUUID(InterfaceStatusHistory.TABLE_NAME));
    history.setChangedAt(LocalDateTime.now());
    history.setReason(request.reason());
  }

  public static void applyUpdate(InterfaceStatusHistory history,
      InterfaceStatusHistoryUpdateRequest request) {
    if (request.toStatusId() != null) {
      // handled in service
    }
    if (request.reason() != null) {
      history.setReason(request.reason());
    }
    history.setChangedAt(LocalDateTime.now());
  }
}

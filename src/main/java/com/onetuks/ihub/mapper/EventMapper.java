package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.communication.EventCreateRequest;
import com.onetuks.ihub.dto.communication.EventResponse;
import com.onetuks.ihub.dto.communication.EventUpdateRequest;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;

public final class EventMapper {

  private EventMapper() {
  }

  public static EventResponse toResponse(Event event) {
    return new EventResponse(
        event.getEventId(),
        event.getProject() != null ? event.getProject().getProjectId() : null,
        event.getTitle(),
        event.getStartDatetime(),
        event.getEndDatetime(),
        event.getLocation(),
        event.getContent(),
        event.getRemindBeforeMinutes(),
        event.getCreatedBy() != null ? event.getCreatedBy().getUserId() : null,
        event.getCreatedAt(),
        event.getUpdatedAt());
  }

  public static Event applyCreate(
      User currentUser, Project project, EventCreateRequest request) {
    LocalDateTime now = LocalDateTime.now();
    return new Event(
        UUIDProvider.provideUUID(Event.TABLE_NAME),
        project,
        request.title(),
        request.startDatetime(),
        request.endDatetime(),
        request.location(),
        request.content(),
        request.remindBeforeMinutes(),
        currentUser,
        now,
        now
    );
  }

  public static void applyUpdate(Event event, EventUpdateRequest request) {
    if (request.title() != null) {
      event.setTitle(request.title());
    }
    if (request.startDatetime() != null) {
      event.setStartDatetime(request.startDatetime());
    }
    if (request.endDatetime() != null) {
      event.setEndDatetime(request.endDatetime());
    }
    if (request.location() != null) {
      event.setLocation(request.location());
    }
    if (request.content() != null) {
      event.setContent(request.content());
    }
    if (request.remindBeforeMinutes() != null) {
      event.setRemindBeforeMinutes(request.remindBeforeMinutes());
    }
    event.setUpdatedAt(LocalDateTime.now());
  }
}

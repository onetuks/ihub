package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.communication.EventCreateRequest;
import com.onetuks.ihub.dto.communication.EventResponse;
import com.onetuks.ihub.dto.communication.EventUpdateRequest;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;
import java.util.Objects;

public final class EventMapper {

  private EventMapper() {
  }

  public static EventResponse toResponse(Event event) {
    return new EventResponse(
        event.getEventId(),
        Objects.requireNonNull(event.getProject()).getProjectId(),
        Objects.requireNonNull(event.getProject()).getStatus(),
        Objects.requireNonNull(event.getProject()).getTitle(),
        event.getTitle(),
        event.getStartAt(),
        event.getEndAt(),
        event.getLocation(),
        event.getContent(),
        event.getRemindBeforeMinutes(),
        Objects.requireNonNull(event.getCreatedBy()).getUserId(),
        Objects.requireNonNull(event.getCreatedBy()).getStatus(),
        Objects.requireNonNull(event.getCreatedBy()).getName(),
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
        request.startAt(),
        request.endAt(),
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
    if (request.startAt() != null) {
      event.setStartAt(request.startAt());
    }
    if (request.endAt() != null) {
      event.setEndAt(request.endAt());
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

package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.communication.EventAttendeeRequest;
import com.onetuks.ihub.dto.communication.EventAttendeeResponse;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.entity.communication.EventAttendee;
import com.onetuks.ihub.entity.user.User;

public final class EventAttendeeMapper {

  private EventAttendeeMapper() {
  }

  public static EventAttendeeResponse toResponse(EventAttendee eventAttendee) {
    return new EventAttendeeResponse(
        eventAttendee.getEventAttendeeId(),
        eventAttendee.getEvent() != null ? eventAttendee.getEvent().getEventId() : null,
        eventAttendee.getUser() != null ? eventAttendee.getUser().getUserId() : null,
        eventAttendee.getIsMandatory(),
        eventAttendee.getAttendStatus());
  }

  public static EventAttendee applyCreate(Event event, User user, EventAttendeeRequest request) {
    return new EventAttendee(
        UUIDProvider.provideUUID(EventAttendee.TABLE_NAME),
        event,
        user,
        request.isMandatory(),
        request.attendStatus()
    );
  }
}

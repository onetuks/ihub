package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.EventAttendeeStatus;

public record EventAttendeeResponse(
    String eventAttendeeId,
    String eventId,
    String userId,
    Boolean isMandatory,
    EventAttendeeStatus attendStatus
) {

}

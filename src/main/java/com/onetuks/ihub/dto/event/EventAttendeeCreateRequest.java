package com.onetuks.ihub.dto.event;

import com.onetuks.ihub.entity.event.EventAttendeeStatus;
import jakarta.validation.constraints.NotNull;

public record EventAttendeeCreateRequest(
    @NotNull String eventId,
    @NotNull String userId,
    Boolean isMandatory,
    EventAttendeeStatus attendStatus
) {

}

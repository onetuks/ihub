package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.EventAttendeeStatus;
import jakarta.validation.constraints.NotNull;

public record EventAttendeeCreateRequest(
    @NotNull String eventId,
    @NotNull String userId,
    Boolean isMandatory,
    EventAttendeeStatus attendStatus
) {

}

package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.EventAttendeeStatus;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record EventAttendeeRequest(
    @NotNull String eventId,
    @NotNull String userId,
    Boolean isMandatory,
    EventAttendeeStatus attendStatus
) {

  public record EventAttendeeRequests(
      List<EventAttendeeRequest> requests
  ) {

  }

}

package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.EventAttendeeStatus;
import com.onetuks.ihub.entity.user.UserStatus;

public record EventAttendeeResponse(
    String eventAttendeeId,
    String eventId,
    String userId,
    UserStatus userStatus,
    String userName,
    Boolean isMandatory,
    EventAttendeeStatus attendStatus
) {

}

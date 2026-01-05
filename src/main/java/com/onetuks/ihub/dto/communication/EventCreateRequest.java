package com.onetuks.ihub.dto.communication;

import java.time.LocalDateTime;

public record EventCreateRequest(
    String title,
    LocalDateTime startDatetime,
    LocalDateTime endDatetime,
    String location,
    String content,
    Integer remindBeforeMinutes
) {

}

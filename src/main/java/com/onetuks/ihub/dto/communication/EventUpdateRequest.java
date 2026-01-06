package com.onetuks.ihub.dto.communication;

import java.time.LocalDateTime;

public record EventUpdateRequest(
    String title,
    LocalDateTime startAt,
    LocalDateTime endAt,
    String location,
    String content,
    Integer remindBeforeMinutes
) {

}

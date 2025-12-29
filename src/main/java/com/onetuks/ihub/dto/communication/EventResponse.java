package com.onetuks.ihub.dto.communication;

import java.time.LocalDateTime;

public record EventResponse(
    String eventId,
    String projectId,
    String title,
    LocalDateTime startDatetime,
    LocalDateTime endDatetime,
    String location,
    String content,
    Integer remindBeforeMinutes,
    String createdById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}

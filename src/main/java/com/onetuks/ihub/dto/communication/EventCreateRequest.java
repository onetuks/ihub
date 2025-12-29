package com.onetuks.ihub.dto.communication;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record EventCreateRequest(
    @NotNull String projectId,
    String title,
    LocalDateTime startDatetime,
    LocalDateTime endDatetime,
    String location,
    String content,
    Integer remindBeforeMinutes
) {

}

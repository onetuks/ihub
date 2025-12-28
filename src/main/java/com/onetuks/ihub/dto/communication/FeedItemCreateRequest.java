package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import jakarta.validation.constraints.NotNull;

public record FeedItemCreateRequest(
    @NotNull String projectId,
    String eventType,
    String actorId,
    TargetType targetType,
    String targetId,
    String summary
) {

}

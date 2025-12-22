package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;

public record FeedItemUpdateRequest(
    String eventType,
    String actorId,
    TargetType targetType,
    String targetId,
    String summary
) {
}

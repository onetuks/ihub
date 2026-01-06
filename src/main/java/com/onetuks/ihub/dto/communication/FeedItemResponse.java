package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record FeedItemResponse(
    String feedId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String eventType,
    String actorId,
    UserStatus actorStatus,
    String actorName,
    TargetType targetType,
    String targetId,
    String summary,
    LocalDateTime createdAt
) {

}

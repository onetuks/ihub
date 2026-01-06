package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record EventResponse(
    String eventId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String title,
    LocalDateTime startAt,
    LocalDateTime endAt,
    String location,
    String content,
    Integer remindBeforeMinutes,
    String createdById,
    UserStatus createdByStatus,
    String createdByName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}

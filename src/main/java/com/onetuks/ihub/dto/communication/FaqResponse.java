package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.FaqStatus;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record FaqResponse(
    String faqId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String category,
    String question,
    String answer,
    Boolean isSecret,
    String assigneeId,
    UserStatus assigneeStatus,
    String assigneeName,
    LocalDateTime answeredAt,
    FaqStatus status,
    String createdById,
    UserStatus createdByStatus,
    String createdByName,
    String updatedById,
    UserStatus updatedByStatus,
    String updatedByName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {

}

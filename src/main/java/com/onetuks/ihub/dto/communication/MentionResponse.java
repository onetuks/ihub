package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record MentionResponse(
    String mentionId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    TargetType targetType,
    String targetId,
    String mentionedUserId,
    UserStatus mentionedUserStatus,
    String mentionedUserName,
    String createdById,
    UserStatus createdByStatus,
    String createdByName,
    LocalDateTime createdAt
) {

}

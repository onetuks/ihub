package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record PostResponse(
    String postId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String title,
    String content,
    String createdById,
    UserStatus createdByStatus,
    String createdByName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}

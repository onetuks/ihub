package com.onetuks.ihub.dto.file;

import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record FolderResponse(
    String folderId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String parentFolderId,
    String name,
    String createdById,
    UserStatus createdByStatus,
    String createdByName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}

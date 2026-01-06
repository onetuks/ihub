package com.onetuks.ihub.dto.file;

import com.onetuks.ihub.entity.file.FileStatus;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record FileResponse(
    String fileId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String folderId,
    FileStatus status,
    String originalName,
    String storedName,
    Long sizeBytes,
    String mimeType,
    String uploadedById,
    UserStatus uploadedByStatus,
    String uploadedByName,
    LocalDateTime uploadedAt,
    LocalDateTime deletedAt
) {

}

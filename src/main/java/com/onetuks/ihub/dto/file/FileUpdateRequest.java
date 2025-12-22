package com.onetuks.ihub.dto.file;

import com.onetuks.ihub.entity.file.FileStatus;
import java.time.LocalDateTime;

public record FileUpdateRequest(
    String folderId,
    FileStatus status,
    String originalName,
    String storedName,
    Long sizeBytes,
    String mimeType,
    String uploadedById,
    LocalDateTime deletedAt
) {
}

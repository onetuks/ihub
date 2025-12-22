package com.onetuks.ihub.dto.file;

import com.onetuks.ihub.entity.file.FileStatus;
import jakarta.validation.constraints.NotNull;

public record FileCreateRequest(
    @NotNull String projectId,
    String folderId,
    @NotNull FileStatus status,
    String originalName,
    String storedName,
    Long sizeBytes,
    String mimeType,
    String uploadedById
) {
}

package com.onetuks.ihub.dto.file;

import jakarta.validation.constraints.NotNull;

public record FolderCreateRequest(
    @NotNull String projectId,
    String parentFolderId,
    String name,
    String createdById
) {
}

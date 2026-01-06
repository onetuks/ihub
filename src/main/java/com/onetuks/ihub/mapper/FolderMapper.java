package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.file.FolderCreateRequest;
import com.onetuks.ihub.dto.file.FolderResponse;
import com.onetuks.ihub.dto.file.FolderUpdateRequest;
import com.onetuks.ihub.entity.file.Folder;
import java.time.LocalDateTime;
import java.util.Objects;

public final class FolderMapper {

  private FolderMapper() {
  }

  public static FolderResponse toResponse(Folder folder) {
    var project = Objects.requireNonNull(folder.getProject());
    var createdBy = Objects.requireNonNull(folder.getCreatedBy());
    return new FolderResponse(
        folder.getFolderId(),
        project.getProjectId(),
        project.getStatus(),
        project.getTitle(),
        folder.getParentFolder() != null ? folder.getParentFolder().getFolderId() : null,
        folder.getName(),
        createdBy.getUserId(),
        createdBy.getStatus(),
        createdBy.getName(),
        folder.getCreatedAt(),
        folder.getUpdatedAt());
  }

  public static void applyCreate(Folder folder, FolderCreateRequest request) {
    LocalDateTime now = LocalDateTime.now();
    folder.setFolderId(UUIDProvider.provideUUID(Folder.TABLE_NAME));
    folder.setName(request.name());
    folder.setCreatedAt(now);
    folder.setUpdatedAt(now);
  }

  public static void applyUpdate(Folder folder, FolderUpdateRequest request) {
    if (request.name() != null) {
      folder.setName(request.name());
    }
    folder.setUpdatedAt(LocalDateTime.now());
  }
}

package com.onetuks.ihub.dto.file;

public record FolderUpdateRequest(
    String parentFolderId,
    String name
) {

}

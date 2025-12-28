package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.communication.TargetType;

public record AttachmentUpdateRequest(
    String fileId,
    TargetType targetType,
    String targetId,
    String attachedById
) {

}

package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.communication.TargetType;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record AttachmentResponse(
    String attachmentId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String fileId,
    TargetType targetType,
    String targetId,
    String attachedById,
    UserStatus attachedByStatus,
    String attachedByName,
    LocalDateTime attachedAt
) {

}

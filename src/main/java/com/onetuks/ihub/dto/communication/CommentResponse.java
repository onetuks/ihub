package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.TargetType;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record CommentResponse(
    String commentId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String parentCommentId,
    TargetType targetType,
    String targetId,
    String content,
    String createdById,
    UserStatus createdByStatus,
    String createdByName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}

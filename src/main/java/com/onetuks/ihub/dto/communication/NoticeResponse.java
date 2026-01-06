package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.NoticeImportance;
import com.onetuks.ihub.entity.communication.NoticeStatus;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record NoticeResponse(
    String noticeId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String title,
    String content,
    String category,
    NoticeImportance importance,
    Boolean isPinned,
    LocalDateTime pinnedAt,
    NoticeStatus status,
    String createdById,
    UserStatus createdByStatus,
    String createdByName,
    String updatedById,
    UserStatus updatedByStatus,
    String updatedByName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {

}

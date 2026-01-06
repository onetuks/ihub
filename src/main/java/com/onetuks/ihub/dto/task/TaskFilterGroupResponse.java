package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.task.TaskFilterGroupDateType;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskFilterGroupResponse(
    String groupId,
    String userId,
    UserStatus userStatus,
    String userName,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String name,
    String assigneeKeyword,
    String authorKeyword,
    TaskFilterGroupDateType dateType,
    LocalDate dateFrom,
    LocalDate dateTo,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {

}

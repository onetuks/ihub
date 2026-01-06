package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.task.TaskPriority;
import com.onetuks.ihub.entity.task.TaskStatus;
import com.onetuks.ihub.entity.task.TaskType;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponse(
    String taskId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String parentTaskId,
    TaskType taskType,
    String interfaceId,
    String title,
    String description,
    TaskStatus status,
    String assigneeId,
    UserStatus assigneeStatus,
    String assigneeName,
    String requesterId,
    UserStatus requesterStatus,
    String requesterName,
    LocalDate startDate,
    LocalDate dueDate,
    TaskPriority priority,
    Integer progress,
    String createdById,
    UserStatus createdByStatus,
    String createdByName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}

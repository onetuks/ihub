package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.task.TaskPriority;
import com.onetuks.ihub.entity.task.TaskStatus;
import com.onetuks.ihub.entity.task.TaskType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record TaskCreateRequest(
    @NotNull String projectId,
    String parentTaskId,
    @NotNull TaskType taskType,
    String interfaceId,
    @NotBlank String title,
    String description,
    @NotNull TaskStatus status,
    String assigneeId,
    String requesterId,
    LocalDate startDate,
    LocalDate dueDate,
    TaskPriority priority,
    Integer progress,
    String createdById
) {
}

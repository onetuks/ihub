package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.task.TaskPriority;
import com.onetuks.ihub.entity.task.TaskStatus;
import com.onetuks.ihub.entity.task.TaskType;
import java.time.LocalDate;

public record TaskUpdateRequest(
    String parentTaskId,
    TaskType taskType,
    String interfaceId,
    String title,
    String description,
    TaskStatus status,
    String assigneeId,
    String requesterId,
    LocalDate startDate,
    LocalDate dueDate,
    TaskPriority priority,
    Integer progress
) {

}

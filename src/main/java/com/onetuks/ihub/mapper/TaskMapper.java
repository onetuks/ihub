package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.task.TaskCreateRequest;
import com.onetuks.ihub.dto.task.TaskResponse;
import com.onetuks.ihub.dto.task.TaskUpdateRequest;
import com.onetuks.ihub.entity.task.Task;
import java.time.LocalDateTime;
import java.util.Objects;

public final class TaskMapper {

  private TaskMapper() {
  }

  public static TaskResponse toResponse(Task task) {
    var project = Objects.requireNonNull(task.getProject());
    var assignee = Objects.requireNonNull(task.getAssignee());
    var requester = Objects.requireNonNull(task.getRequester());
    var createdBy = Objects.requireNonNull(task.getCreatedBy());
    return new TaskResponse(
        task.getTaskId(),
        project.getProjectId(),
        project.getStatus(),
        project.getTitle(),
        task.getParentTask() != null ? task.getParentTask().getTaskId() : null,
        task.getTaskType(),
        task.getAnInterface() != null ? task.getAnInterface().getInterfaceId() : null,
        task.getTitle(),
        task.getDescription(),
        task.getStatus(),
        assignee.getUserId(),
        assignee.getStatus(),
        assignee.getName(),
        requester.getUserId(),
        requester.getStatus(),
        requester.getName(),
        task.getStartDate(),
        task.getDueDate(),
        task.getPriority(),
        task.getProgress(),
        createdBy.getUserId(),
        createdBy.getStatus(),
        createdBy.getName(),
        task.getCreatedAt(),
        task.getUpdatedAt());
  }

  public static void applyCreate(Task task, TaskCreateRequest request) {
    LocalDateTime now = LocalDateTime.now();
    task.setTaskId(UUIDProvider.provideUUID(Task.TABLE_NAME));
    task.setTaskType(request.taskType());
    task.setTitle(request.title());
    task.setDescription(request.description());
    task.setStatus(request.status());
    task.setStartDate(request.startDate());
    task.setDueDate(request.dueDate());
    task.setPriority(request.priority());
    task.setProgress(request.progress());
    task.setCreatedAt(now);
    task.setUpdatedAt(now);
  }

  public static void applyUpdate(Task task, TaskUpdateRequest request) {
    if (request.taskType() != null) {
      task.setTaskType(request.taskType());
    }
    if (request.title() != null) {
      task.setTitle(request.title());
    }
    if (request.description() != null) {
      task.setDescription(request.description());
    }
    if (request.status() != null) {
      task.setStatus(request.status());
    }
    if (request.startDate() != null) {
      task.setStartDate(request.startDate());
    }
    if (request.dueDate() != null) {
      task.setDueDate(request.dueDate());
    }
    if (request.priority() != null) {
      task.setPriority(request.priority());
    }
    if (request.progress() != null) {
      task.setProgress(request.progress());
    }
    task.setUpdatedAt(LocalDateTime.now());
  }
}

package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.task.TaskFilterGroupStatusType;
import jakarta.validation.constraints.NotNull;

public record TaskFilterGroupStatusCreateRequest(
    @NotNull String groupId,
    @NotNull TaskFilterGroupStatusType statusType
) {

}

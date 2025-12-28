package com.onetuks.ihub.dto.task;

import com.onetuks.ihub.entity.task.TaskFilterGroupDateType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record TaskFilterGroupCreateRequest(
    @NotNull String userId,
    @NotNull String projectId,
    @NotBlank String name,
    String assigneeKeyword,
    String authorKeyword,
    @NotNull TaskFilterGroupDateType dateType,
    LocalDate dateFrom,
    LocalDate dateTo
) {

}

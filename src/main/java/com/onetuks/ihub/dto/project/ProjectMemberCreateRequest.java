package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.project.ProjectMemberRole;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ProjectMemberCreateRequest(
    @NotNull String projectId,
    @NotNull String userId,
    @NotNull ProjectMemberRole role,
    LocalDateTime joinedAt,
    LocalDateTime leftAt
) {
}

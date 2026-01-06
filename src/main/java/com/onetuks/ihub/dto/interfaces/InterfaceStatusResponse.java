package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.project.ProjectStatus;
import java.time.LocalDateTime;

public record InterfaceStatusResponse(
    String statusId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String name,
    String code,
    Integer seqOrder,
    Boolean isDefault,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}

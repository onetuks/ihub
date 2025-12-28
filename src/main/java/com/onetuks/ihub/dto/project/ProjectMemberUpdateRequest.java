package com.onetuks.ihub.dto.project;

import com.onetuks.ihub.entity.project.ProjectMemberRole;

public record ProjectMemberUpdateRequest(
    ProjectMemberRole role
) {

}

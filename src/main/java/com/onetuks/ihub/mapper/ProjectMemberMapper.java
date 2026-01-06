package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.project.ProjectMemberResponse;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.project.ProjectMember;
import com.onetuks.ihub.entity.project.ProjectMemberRole;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;
import java.util.Objects;

public final class ProjectMemberMapper {

  private ProjectMemberMapper() {
  }

  public static ProjectMemberResponse toResponse(ProjectMember projectMember) {
    var project = Objects.requireNonNull(projectMember.getProject());
    var user = Objects.requireNonNull(projectMember.getUser());
    return new ProjectMemberResponse(
        projectMember.getProjectMemberId(),
        project.getProjectId(),
        project.getStatus(),
        project.getTitle(),
        user.getUserId(),
        user.getStatus(),
        user.getName(),
        projectMember.getRole(),
        projectMember.getJoinedAt(),
        projectMember.getLeftAt());
  }

  public static ProjectMember applyCreate(Project project, User currentUser) {
    return new ProjectMember(
        UUIDProvider.provideUUID(ProjectMember.TABLE_NAME),
        project,
        currentUser,
        ProjectMemberRole.PROJECT_OWNER,
        LocalDateTime.now(),
        null);
  }
}

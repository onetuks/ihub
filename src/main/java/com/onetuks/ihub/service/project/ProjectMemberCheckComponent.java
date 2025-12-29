package com.onetuks.ihub.service.project;

import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ProjectMemberCheckComponent {

  private final ProjectMemberJpaRepository projectMemberRepository;

  @Transactional(readOnly = true)
  public void checkIsProjectMember(User user, String projectId) {
    boolean isProjectMember = projectMemberRepository.existsByProject_ProjectIdAndUser(projectId, user);
    if (!isProjectMember) {
      throw new AccessDeniedException("프로젝트 멤버가 아닙니다.");
    }
  }
}

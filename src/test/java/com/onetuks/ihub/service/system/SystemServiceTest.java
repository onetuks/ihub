package com.onetuks.ihub.service.system;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.system.SystemCreateRequest;
import com.onetuks.ihub.dto.system.SystemUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.system.System;
import com.onetuks.ihub.entity.system.SystemEnvironment;
import com.onetuks.ihub.entity.system.SystemStatus;
import com.onetuks.ihub.entity.system.SystemType;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class SystemServiceTest {

  @Autowired
  private SystemService systemService;

  @Autowired
  private ProjectJpaRepository projectJpaRepository;
  @Autowired
  private UserJpaRepository userJpaRepository;
  @Autowired
  private ProjectMemberJpaRepository projectMemberJpaRepository;

  private User creator;
  private User updater;
  private Project project;

  @BeforeEach
  void setUp() {
    creator = ServiceTestDataFactory.createUser(userJpaRepository);
    updater = ServiceTestDataFactory.createUser(userJpaRepository);
    project = ServiceTestDataFactory.createProject(
        projectJpaRepository, creator, updater, "SysProj");
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, creator);
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, updater);
  }

  @Test
  void createSystem_success() {
    SystemCreateRequest request = buildCreateRequest();

    System result = systemService.create(creator, project.getProjectId(), request);

    assertThat(result.getSystemId()).isNotNull();
    assertThat(result.getSystemCode()).isEqualTo(request.systemCode());
    assertThat(result.getStatus()).isEqualTo(SystemStatus.ACTIVE);
  }

  @Test
  void updateSystem_success() {
    System created = systemService.create(creator, project.getProjectId(), buildCreateRequest());
    SystemUpdateRequest updateRequest = new SystemUpdateRequest(
        "SYS2-NEW",
        SystemStatus.INACTIVE,
        "updated",
        SystemType.SERVER,
        SystemEnvironment.QA);

    System result = systemService.update(updater, created.getSystemId(), updateRequest);

    assertThat(result.getSystemCode()).isEqualTo(updateRequest.systemCode());
    assertThat(result.getStatus()).isEqualTo(updateRequest.status());
  }

  @Test
  void getSystemById_exception() {
    System system = systemService.create(creator, project.getProjectId(), buildCreateRequest());
    User hacker = ServiceTestDataFactory.createUser(userJpaRepository);

    assertThatThrownBy(() -> systemService.getById(hacker, system.getSystemId()))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Test
  void getSystems_returnsAll() {
    Pageable pageable = PageRequest.of(0, 10);
    systemService.create(creator, project.getProjectId(), buildCreateRequest());
    systemService.create(creator, project.getProjectId(), buildCreateRequest());

    Page<System> results = systemService.getAll(project.getProjectId(), pageable);

    assertThat(results.getTotalElements()).isGreaterThanOrEqualTo(2);
  }

  @Test
  void deleteSystem_success() {
    System created = systemService.create(updater, project.getProjectId(), buildCreateRequest());

    systemService.delete(updater, created.getSystemId());

    assertThatThrownBy(() -> systemService.getById(updater, created.getSystemId()))
        .isInstanceOf(EntityNotFoundException.class);
  }

  private SystemCreateRequest buildCreateRequest() {
    return new SystemCreateRequest(
        "SYS1",
        "desc",
        SystemType.DB,
        SystemEnvironment.DEV
    );
  }
}

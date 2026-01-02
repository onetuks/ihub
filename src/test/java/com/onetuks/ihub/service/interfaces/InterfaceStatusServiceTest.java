package com.onetuks.ihub.service.interfaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusUpdateRequest;
import com.onetuks.ihub.entity.interfaces.InterfaceStatus;
import com.onetuks.ihub.entity.project.Project;
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
class InterfaceStatusServiceTest {

  @Autowired
  private InterfaceStatusService interfaceStatusService;

  @Autowired
  private ProjectJpaRepository projectJpaRepository;
  @Autowired
  private UserJpaRepository userJpaRepository;
  @Autowired
  private ProjectMemberJpaRepository projectMemberJpaRepository;

  private Project project;
  private User user;

  @BeforeEach
  void setUp() {
    user = ServiceTestDataFactory.createUser(userJpaRepository);
    project = ServiceTestDataFactory.createProject(projectJpaRepository, user, user,
        "IFStatusProj");
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, user);
  }

  @Test
  void createInterfaceStatus_success() {
    InterfaceStatusCreateRequest request = buildCreateRequest();

    InterfaceStatus result = interfaceStatusService.create(user, request);

    assertThat(result.getStatusId()).isNotNull();
    assertThat(result.getName()).isEqualToIgnoringCase("ready");
    assertThat(result.getSeqOrder()).isOne();
  }

  @Test
  void updateInterfaceStatus_success() {
    InterfaceStatus created = interfaceStatusService.create(user, buildCreateRequest());

    InterfaceStatusUpdateRequest updateRequest =
        new InterfaceStatusUpdateRequest("DraftUpdated", "DFU", 2, false);

    InterfaceStatus result =
        interfaceStatusService.update(user, created.getStatusId(), updateRequest);

    assertThat(result.getName()).isEqualTo(updateRequest.name());
    assertThat(result.getSeqOrder()).isEqualTo(updateRequest.seqOrder());
  }

  @Test
  void updateInterfaceStatus_exception() {
    InterfaceStatus created = interfaceStatusService.create(user, buildCreateRequest());
    InterfaceStatusUpdateRequest updateRequest =
        new InterfaceStatusUpdateRequest("DraftUpdated", "DFU", 2, false);
    User hacker = ServiceTestDataFactory.createUser(userJpaRepository);

    assertThatThrownBy(
        () -> interfaceStatusService.update(hacker, created.getStatusId(), updateRequest))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Test
  void getInterfaceStatuses_returnsAll() {
    Pageable pageable = PageRequest.of(0, 10);
    interfaceStatusService.create(user, buildCreateRequest());
    interfaceStatusService.create(user, buildCreateRequest());

    Page<InterfaceStatus> results = interfaceStatusService.getAll(pageable);

    assertThat(results.getTotalElements()).isGreaterThanOrEqualTo(2);
  }

  @Test
  void deleteInterfaceStatus_success() {
    InterfaceStatus created = interfaceStatusService.create(user, buildCreateRequest());

    interfaceStatusService.delete(user, created.getStatusId());

    assertThatThrownBy(() -> interfaceStatusService.getById(created.getStatusId()))
        .isInstanceOf(EntityNotFoundException.class);
  }

  private InterfaceStatusCreateRequest buildCreateRequest() {
    return new InterfaceStatusCreateRequest(
        project.getProjectId(),
        "Ready",
        "RD",
        1,
        true
    );
  }
}

package com.onetuks.ihub.service.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusUpdateRequest;
import com.onetuks.ihub.entity.interfaces.InterfaceStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.InterfaceStatusMapper;
import com.onetuks.ihub.repository.InterfaceStatusJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.service.project.ProjectMemberCheckComponent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterfaceStatusService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;
  private final InterfaceStatusJpaRepository interfaceStatusJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;

  @Transactional
  public InterfaceStatus create(User user, InterfaceStatusCreateRequest request) {
    projectMemberCheckComponent.checkIsProjectOwner(user.getUserId(), request.projectId());
    return interfaceStatusJpaRepository.save(
        InterfaceStatusMapper.applyCreate(findProject(request.projectId()), request));
  }

  @Transactional(readOnly = true)
  public InterfaceStatus getById(String statusId) {
    return findEntity(statusId);
  }

  @Transactional(readOnly = true)
  public Page<InterfaceStatus> getAll(Pageable pageable) {
    return interfaceStatusJpaRepository.findAll(pageable);
  }

  @Transactional
  public InterfaceStatus update(User user, String statusId, InterfaceStatusUpdateRequest request) {
    InterfaceStatus status = findEntity(statusId);
    projectMemberCheckComponent.checkIsProjectOwner(
        user.getUserId(), status.getProject().getProjectId());
    return InterfaceStatusMapper.applyUpdate(status, request);
  }

  @Transactional
  public void delete(User user, String statusId) {
    InterfaceStatus status = findEntity(statusId);
    projectMemberCheckComponent.checkIsProjectOwner(
        user.getUserId(), status.getProject().getProjectId());
    interfaceStatusJpaRepository.delete(status);
  }

  private InterfaceStatus findEntity(String statusId) {
    return interfaceStatusJpaRepository.findById(statusId)
        .orElseThrow(() -> new EntityNotFoundException("Interface status not found: " + statusId));
  }

  private Project findProject(String projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }
}

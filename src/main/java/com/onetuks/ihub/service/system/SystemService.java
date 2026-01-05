package com.onetuks.ihub.service.system;

import com.onetuks.ihub.dto.system.SystemCreateRequest;
import com.onetuks.ihub.dto.system.SystemUpdateRequest;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.system.System;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.SystemMapper;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.SystemJpaRepository;
import com.onetuks.ihub.service.project.ProjectMemberCheckComponent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SystemService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;
  private final SystemJpaRepository systemJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;

  @Transactional
  public System create(User currentUser, String projectId, SystemCreateRequest request) {
    Project project = findProject(projectId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser, project.getProjectId());
    return systemJpaRepository.save(SystemMapper.applyCreate(project, currentUser, request));
  }

  @Transactional(readOnly = true)
  public System getById(User currentUser, String systemId) {
    System system = findEntity(systemId);
    projectMemberCheckComponent.checkIsProjectMember(
        currentUser, system.getProject().getProjectId());
    return system;
  }

  @Transactional(readOnly = true)
  public Page<System> getAll(String projectId, Pageable pageable) {
    return systemJpaRepository.findAllByProject_ProjectId(projectId, pageable);
  }

  @Transactional
  public System update(User currentUser, String systemId, SystemUpdateRequest request) {
    return SystemMapper.applyUpdate(findEntity(systemId), currentUser, request);
  }

  @Transactional
  public void delete(User currentUser, String systemId) {
    System system = findEntity(systemId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser,
        system.getProject().getProjectId());
    systemJpaRepository.delete(system);
  }

  private System findEntity(String systemId) {
    return systemJpaRepository.findById(systemId)
        .orElseThrow(() -> new EntityNotFoundException("System not found: " + systemId));
  }

  private Project findProject(String projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }
}

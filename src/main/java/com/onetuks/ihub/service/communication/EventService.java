package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.dto.communication.EventCreateRequest;
import com.onetuks.ihub.dto.communication.EventUpdateRequest;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.EventMapper;
import com.onetuks.ihub.repository.EventJpaRepository;
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
public class EventService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;

  private final AlarmService alarmService;

  private final EventJpaRepository eventJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;

  @Transactional
  public Event create(User currentUser, String projectId, EventCreateRequest request) {
    projectMemberCheckComponent.checkIsProjectMember(currentUser.getUserId(), projectId);
    Event event = eventJpaRepository.save(
        EventMapper.applyCreate(currentUser, findProject(projectId), request));
    alarmService.create(event);
    return event;
  }

  @Transactional(readOnly = true)
  public Event getById(User currentUser, String eventId) {
    Event event = findEntity(eventId);
    projectMemberCheckComponent.checkIsProjectViewer(
        currentUser.getUserId(), event.getProject().getProjectId());
    return event;
  }

  @Transactional(readOnly = true)
  public Page<Event> getAll(User currentUser, String projectId, Pageable pageable) {
    projectMemberCheckComponent.checkIsProjectViewer(currentUser.getUserId(), projectId);
    return eventJpaRepository.findAllByProject_ProjectId(projectId, pageable);
  }

  @Transactional
  public Event update(User currentUser, String eventId, EventUpdateRequest request) {
    Event event = findEntity(eventId);
    projectMemberCheckComponent.checkIsProjectMember(
        currentUser.getUserId(), event.getProject().getProjectId());
    EventMapper.applyUpdate(event, request);
    alarmService.update(event);
    return event;
  }

  @Transactional
  public void delete(User currentUser, String eventId) {
    Event event = findEntity(eventId);
    projectMemberCheckComponent.checkIsProjectMember(
        currentUser.getUserId(), event.getProject().getProjectId());
    alarmService.delete(event);
    eventJpaRepository.delete(event);
  }

  private Event findEntity(String eventId) {
    return eventJpaRepository.findById(eventId)
        .orElseThrow(() -> new EntityNotFoundException("Event not found: " + eventId));
  }

  private Project findProject(String projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }
}

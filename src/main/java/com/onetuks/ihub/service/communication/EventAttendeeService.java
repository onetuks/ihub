package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.dto.communication.EventAttendeeRequest.EventAttendeeRequests;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.entity.communication.EventAttendee;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.EventAttendeeMapper;
import com.onetuks.ihub.repository.EventAttendeeJpaRepository;
import com.onetuks.ihub.repository.EventJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.project.ProjectMemberCheckComponent;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventAttendeeService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;
  private final EventAttendeeJpaRepository eventAttendeeRepository;
  private final EventJpaRepository eventRepository;
  private final UserJpaRepository userRepository;


  public List<EventAttendee> manageAttendees(
      User currentUser, String eventId, EventAttendeeRequests requests) {
    Event event = findEvent(eventId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser,
        event.getProject().getProjectId());

    eventAttendeeRepository.deleteAllByEvent_EventId(eventId);

    List<EventAttendee> eventAttendees = requests.requests().stream()
        .map(request ->
            EventAttendeeMapper.applyCreate(
                event,
                userRepository.findById(request.userId())
                    .orElseThrow(EntityNotFoundException::new),
                request))
        .toList();
    return eventAttendeeRepository.saveAll(eventAttendees);
  }

  public Page<EventAttendee> getAllAttendees(User currentUser, String eventId, Pageable pageable) {
    Event event = findEvent(eventId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser,
        event.getProject().getProjectId());
    return eventAttendeeRepository.findAllByEvent_EventId(eventId, pageable);
  }

  private Event findEvent(String eventId) {
    return eventRepository.findById(eventId).orElseThrow(EntityNotFoundException::new);
  }
}

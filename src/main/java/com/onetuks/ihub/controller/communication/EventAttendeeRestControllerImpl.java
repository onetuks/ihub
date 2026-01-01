package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.EventAttendeeRequest.EventAttendeeRequests;
import com.onetuks.ihub.dto.communication.EventAttendeeResponse;
import com.onetuks.ihub.entity.communication.EventAttendee;
import com.onetuks.ihub.mapper.EventAttendeeMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.communication.EventAttendeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventAttendeeRestControllerImpl implements EventAttendeeRestController {

  private final CurrentUserProvider currentUserProvider;
  private final EventAttendeeService eventAttendeeService;

  @Override
  public ResponseEntity<List<EventAttendeeResponse>> manageEventAttendees(
      String eventId, EventAttendeeRequests request) {
    List<EventAttendee> results = eventAttendeeService.manageAttendees(currentUserProvider.resolveUser(), eventId, request);
    return ResponseEntity.ok(results.stream().map(EventAttendeeMapper::toResponse).toList());
  }

  @Override
  public ResponseEntity<Page<EventAttendeeResponse>> getEventAttendees(
      String eventId, Pageable pageable) {
    Page<EventAttendee> results = eventAttendeeService.getAllAttendees(currentUserProvider.resolveUser(), eventId, pageable);
    return ResponseEntity.ok(results.map(EventAttendeeMapper::toResponse));
  }
}

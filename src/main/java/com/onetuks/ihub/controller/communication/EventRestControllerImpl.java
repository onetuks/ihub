package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.EventCreateRequest;
import com.onetuks.ihub.dto.communication.EventResponse;
import com.onetuks.ihub.dto.communication.EventUpdateRequest;
import com.onetuks.ihub.entity.communication.Event;
import com.onetuks.ihub.mapper.EventMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.communication.EventService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventRestControllerImpl implements EventRestController {

  private final CurrentUserProvider currentUserProvider;
  private final EventService eventService;

  @Override
  public ResponseEntity<EventResponse> createEvent(
      @Valid @RequestBody EventCreateRequest request) {
    Event result = eventService.create(currentUserProvider.resolveUser(), request);
    EventResponse response = EventMapper.toResponse(result);
    return ResponseEntity.created(URI.create("/api/events/" + response.eventId()))
        .body(response);
  }

  @Override
  public ResponseEntity<EventResponse> getEvent(@PathVariable String eventId) {
    Event result = eventService.getById(currentUserProvider.resolveUser(), eventId);
    return ResponseEntity.ok(EventMapper.toResponse(result));
  }

  @Override
  public ResponseEntity<Page<EventResponse>> getEvents(
      @RequestParam String projectId, @PageableDefault Pageable pageable) {
    Page<Event> results =
        eventService.getAll(currentUserProvider.resolveUser(), projectId, pageable);
    return ResponseEntity.ok(results.map(EventMapper::toResponse));
  }

  @Override
  public ResponseEntity<EventResponse> updateEvent(
      @PathVariable String eventId, @Valid @RequestBody EventUpdateRequest request) {
    Event result = eventService.update(currentUserProvider.resolveUser(), eventId, request);
    return ResponseEntity.ok(EventMapper.toResponse(result));
  }

  @Override
  public ResponseEntity<Void> deleteEvent(@PathVariable String eventId) {
    eventService.delete(currentUserProvider.resolveUser(), eventId);
    return ResponseEntity.noContent().build();
  }
}

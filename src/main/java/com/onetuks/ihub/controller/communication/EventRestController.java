package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.EventAttendeeRequest;
import com.onetuks.ihub.dto.communication.EventAttendeeResponse;
import com.onetuks.ihub.dto.communication.EventCreateRequest;
import com.onetuks.ihub.dto.communication.EventResponse;
import com.onetuks.ihub.dto.communication.EventUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/events")
@Tag(name = "Event", description = "Event management APIs")
public interface EventRestController {

  @Operation(summary = "Create event")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Event created"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "401", description = "UnAuthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping
  ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventCreateRequest request);

  @Operation(summary = "Get event by id")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Event found"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "401", description = "UnAuthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "Event not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{eventId}")
  ResponseEntity<EventResponse> getEvent(@PathVariable String eventId);

  @Operation(summary = "List events")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Events listed"),
      @ApiResponse(responseCode = "401", description = "UnAuthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<Page<EventResponse>> getEvents(
      @RequestParam String projectId,
      @PageableDefault Pageable pageable);

  @Operation(summary = "Update event")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Event updated"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "401", description = "UnAuthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "Event not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{eventId}")
  ResponseEntity<EventResponse> updateEvent(
      @PathVariable String eventId,
      @Valid @RequestBody EventUpdateRequest request);

  @Operation(summary = "Delete event")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Event deleted"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "401", description = "UnAuthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "Event not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{eventId}")
  ResponseEntity<Void> deleteEvent(@PathVariable String eventId);

  @Operation(summary = "Manage event attendees")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Event attendees managed"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "401", description = "UnAuthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "Event not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{eventId}/attendees")
  ResponseEntity<List<EventAttendeeResponse>> manageEventAttendees(
      @PathVariable String eventId,
      @Valid @RequestBody EventAttendeeRequest.EventAttendeeRequests request);

  @Operation(summary = "Get event attendees")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Get Event Attendees"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "401", description = "UnAuthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "Event not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{eventId}/attendees")
  ResponseEntity<Page<EventAttendeeResponse>> getEventAttendees(
      @PathVariable String eventId,
      @PageableDefault Pageable pageable);
}

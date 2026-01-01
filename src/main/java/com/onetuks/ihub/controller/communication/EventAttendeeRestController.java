package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.EventAttendeeRequest;
import com.onetuks.ihub.dto.communication.EventAttendeeResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/api/events")
@Tag(name = "Event Attendee", description = "이벤트 참여자 APIs")
public interface EventAttendeeRestController {

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

package com.onetuks.ihub.controller.system;

import com.onetuks.ihub.dto.system.SystemCreateRequest;
import com.onetuks.ihub.dto.system.SystemResponse;
import com.onetuks.ihub.dto.system.SystemUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Tag(name = "System", description = "System management APIs")
public interface SystemRestController {

  @Operation(summary = "List systems")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Systems listed"),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/api/projects/{projectId}/systems")
  ResponseEntity<Page<SystemResponse>> getSystems(
      @PathVariable String projectId,
      @PageableDefault Pageable pageable);

  @Operation(summary = "Create system")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "System created"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping("/api/projects/{projectId}/systems")
  ResponseEntity<SystemResponse> createSystem(
      @PathVariable String projectId,
      @Valid @RequestBody SystemCreateRequest request);

  @Operation(summary = "Get system by id")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "System found"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "System not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/api/systems/{systemId}")
  ResponseEntity<SystemResponse> getSystem(@PathVariable String systemId);

  @Operation(summary = "Update system")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "System updated"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "System not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PatchMapping("/api/systems/{systemId}")
  ResponseEntity<SystemResponse> updateSystem(
      @PathVariable String systemId,
      @Valid @RequestBody SystemUpdateRequest request);

  @Operation(summary = "Delete system")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "System deleted"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "System not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/api/systems/{systemId}")
  ResponseEntity<Void> deleteSystem(@PathVariable String systemId);
}

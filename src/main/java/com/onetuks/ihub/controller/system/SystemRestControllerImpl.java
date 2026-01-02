package com.onetuks.ihub.controller.system;

import static com.onetuks.ihub.config.RoleDataInitializer.SYSTEM_FULL_ACCESS;

import com.onetuks.ihub.annotation.RequiresRole;
import com.onetuks.ihub.dto.system.SystemCreateRequest;
import com.onetuks.ihub.dto.system.SystemResponse;
import com.onetuks.ihub.dto.system.SystemUpdateRequest;
import com.onetuks.ihub.entity.system.System;
import com.onetuks.ihub.mapper.SystemMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.system.SystemService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SystemRestControllerImpl implements SystemRestController {

  private final CurrentUserProvider currentUserProvider;
  private final SystemService systemService;

  @RequiresRole({SYSTEM_FULL_ACCESS})
  @Override
  public ResponseEntity<SystemResponse> createSystem(
      @Valid @RequestBody SystemCreateRequest request) {
    System result = systemService.create(currentUserProvider.resolveUser(), request);
    SystemResponse response = SystemMapper.toResponse(result);
    return ResponseEntity
        .created(URI.create("/api/systems/" + response.systemId()))
        .body(response);
  }

  @RequiresRole({SYSTEM_FULL_ACCESS})
  @Override
  public ResponseEntity<SystemResponse> getSystem(@PathVariable String systemId) {
    System result = systemService.getById(currentUserProvider.resolveUser(), systemId);
    SystemResponse response = SystemMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Page<SystemResponse>> getSystems(@PageableDefault Pageable pageable) {
    Page<System> results = systemService.getAll(pageable);
    Page<SystemResponse> responses = results.map(SystemMapper::toResponse);
    return ResponseEntity.ok(responses);
  }

  @RequiresRole({SYSTEM_FULL_ACCESS})
  @Override
  public ResponseEntity<SystemResponse> updateSystem(
      @PathVariable String systemId, @Valid @RequestBody SystemUpdateRequest request) {
    System result = systemService.update(currentUserProvider.resolveUser(), systemId, request);
    SystemResponse response = SystemMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @RequiresRole({SYSTEM_FULL_ACCESS})
  @Override
  public ResponseEntity<Void> deleteSystem(@PathVariable String systemId) {
    systemService.delete(currentUserProvider.resolveUser(), systemId);
    return ResponseEntity.noContent().build();
  }
}

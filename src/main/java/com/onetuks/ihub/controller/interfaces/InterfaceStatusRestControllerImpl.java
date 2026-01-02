package com.onetuks.ihub.controller.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusUpdateRequest;
import com.onetuks.ihub.entity.interfaces.InterfaceStatus;
import com.onetuks.ihub.mapper.InterfaceStatusMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.interfaces.InterfaceStatusService;
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
public class InterfaceStatusRestControllerImpl implements InterfaceStatusRestController {

  private final CurrentUserProvider currentUserProvider;
  private final InterfaceStatusService interfaceStatusService;

  @Override
  public ResponseEntity<InterfaceStatusResponse> createInterfaceStatus(
      @Valid @RequestBody InterfaceStatusCreateRequest request
  ) {
    InterfaceStatus result = interfaceStatusService.create(currentUserProvider.resolveUser(),
        request);
    InterfaceStatusResponse response = InterfaceStatusMapper.toResponse(result);
    return ResponseEntity
        .created(URI.create("/api/interface-statuses/" + response.statusId()))
        .body(response);
  }

  @Override
  public ResponseEntity<InterfaceStatusResponse> getInterfaceStatus(@PathVariable String statusId) {
    InterfaceStatus result = interfaceStatusService.getById(statusId);
    InterfaceStatusResponse response = InterfaceStatusMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Page<InterfaceStatusResponse>> getInterfaceStatuses(
      @PageableDefault Pageable pageable
  ) {
    Page<InterfaceStatus> results = interfaceStatusService.getAll(pageable);
    Page<InterfaceStatusResponse> responses = results.map(InterfaceStatusMapper::toResponse);
    return ResponseEntity.ok(responses);
  }

  @Override
  public ResponseEntity<InterfaceStatusResponse> updateInterfaceStatus(
      @PathVariable String statusId, @Valid @RequestBody InterfaceStatusUpdateRequest request
  ) {
    InterfaceStatus result = interfaceStatusService.update(currentUserProvider.resolveUser(),
        statusId, request);
    InterfaceStatusResponse response = InterfaceStatusMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> deleteInterfaceStatus(@PathVariable String statusId) {
    interfaceStatusService.delete(currentUserProvider.resolveUser(), statusId);
    return ResponseEntity.noContent().build();
  }
}

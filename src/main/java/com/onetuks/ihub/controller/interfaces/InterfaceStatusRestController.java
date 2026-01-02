package com.onetuks.ihub.controller.interfaces;

import com.onetuks.ihub.dto.interfaces.InterfaceStatusCreateRequest;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusResponse;
import com.onetuks.ihub.dto.interfaces.InterfaceStatusUpdateRequest;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/interface-statuses")
@Tag(name = "InterfaceStatus", description = "인터페이스 상태 관리 APIs")
public interface InterfaceStatusRestController {

  @Operation(summary = "인터페이스 상태 생성")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "생성 성공"),
      @ApiResponse(responseCode = "400", description = "부적절한 요청"),
      @ApiResponse(responseCode = "401", description = "인증 필요"),
      @ApiResponse(responseCode = "403", description = "권한 없음"),
      @ApiResponse(responseCode = "500", description = "서버 에러")
  })
  @PostMapping
  ResponseEntity<InterfaceStatusResponse> createInterfaceStatus(
      @Valid @RequestBody InterfaceStatusCreateRequest request);

  @Operation(summary = "인터페이스 상태 조회")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "조회 성공"),
      @ApiResponse(responseCode = "400", description = "부적절한 식별자"),
      @ApiResponse(responseCode = "404", description = "Interface status not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{statusId}")
  ResponseEntity<InterfaceStatusResponse> getInterfaceStatus(
      @PathVariable String statusId);

  @Operation(summary = "인터페이스 상태 목록 조회")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Interface statuses listed"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<Page<InterfaceStatusResponse>> getInterfaceStatuses(
      @PageableDefault Pageable pageable);

  @Operation(summary = "Update interface status")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Interface status updated"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "401", description = "인증 필요"),
      @ApiResponse(responseCode = "403", description = "권한 없음"),
      @ApiResponse(responseCode = "404", description = "Interface status not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PutMapping("/{statusId}")
  ResponseEntity<InterfaceStatusResponse> updateInterfaceStatus(
      @PathVariable String statusId,
      @Valid @RequestBody InterfaceStatusUpdateRequest request);

  @Operation(summary = "Delete interface status")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Interface status deleted"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "401", description = "인증 필요"),
      @ApiResponse(responseCode = "403", description = "권한 없음"),
      @ApiResponse(responseCode = "404", description = "Interface status not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{statusId}")
  ResponseEntity<Void> deleteInterfaceStatus(@PathVariable String statusId);
}

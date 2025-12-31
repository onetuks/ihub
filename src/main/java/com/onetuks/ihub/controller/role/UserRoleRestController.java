package com.onetuks.ihub.controller.role;

import com.onetuks.ihub.dto.role.UserRoleRequest;
import com.onetuks.ihub.dto.role.UserRoleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Tag(name = "UserRole", description = "유저 권한 설정")
public interface UserRoleRestController {

  @Operation(summary = "유저의 권한을 모두 조회합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "유저의 권한을 모두 조회합니다."),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "500", description = "서버 에러")
  })
  @GetMapping("/api/users/{userId}/roles")
  ResponseEntity<List<UserRoleResponse>> getAllUserRole(@PathVariable String userId);

  @Operation(summary = "유저의 권한을 모두 교체합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "유저의 권한을 모두 교체합니다."),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "500", description = "서버 에러")
  })
  @PutMapping(path = "/api/users/{userId}/roles")
  ResponseEntity<List<UserRoleResponse>> updateUserRole(
      @PathVariable String userId,
      @Valid @RequestBody UserRoleRequest request);

  @Operation(summary = "유저에게 특정 권한을 부여합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "유저에게 특정 권한을 부여합니다."),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "500", description = "서버 에러")
  })
  @PostMapping(path = "/api/users/{userId}/roles/{roleId}")
  ResponseEntity<UserRoleResponse> grantUserRole(
      @PathVariable String userId,
      @PathVariable String roleId);


  @Operation(summary = "유저에게 특정 권한을 회수합니다.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "유저에게 특정 권한을 회수합니다."),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "500", description = "서버 에러")
  })
  @DeleteMapping(path = "/api/users/{userId}/roles/{roleId}")
  ResponseEntity<UserRoleResponse> revokeUserRole(
      @PathVariable String userId,
      @PathVariable String roleId);
}

package com.onetuks.ihub.controller.user;

import com.onetuks.ihub.dto.user.UserCreateRequest;
import com.onetuks.ihub.dto.user.UserPasswordUpdateRequest;
import com.onetuks.ihub.dto.user.UserResponse;
import com.onetuks.ihub.dto.user.UserStatusUpdateRequest;
import com.onetuks.ihub.dto.user.UserUpdateRequest;
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
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/users")
@Tag(name = "User", description = "User management APIs")
public interface UserRestController {

  @Operation(summary = "Create user")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "User created"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "401", description = "No Authorization"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping("/invitations")
  ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request);

  @Operation(summary = "Get user by id")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User found"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "401", description = "No Authorization"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "User not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/{userId}")
  ResponseEntity<UserResponse> getUserById(@PathVariable String userId);

  @Operation(summary = "Search User")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User found"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "401", description = "No Authorization"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "User not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping(path = "/search")
  ResponseEntity<Page<UserResponse>> getUsers(
      @RequestParam String query,
      @PageableDefault Pageable pageable);

  @Operation(summary = "List users")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Users listed"),
      @ApiResponse(responseCode = "401", description = "No Authorization"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  ResponseEntity<Page<UserResponse>> getUsers(@PageableDefault Pageable pageable);

  @Operation(summary = "Update user")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User updated"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "401", description = "No Authorization"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "User not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PatchMapping("/{userId}")
  ResponseEntity<UserResponse> updateUser(
      @PathVariable String userId,
      @Valid @RequestBody UserUpdateRequest request);

  @Operation(summary = "Update user password")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User password updated"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "401", description = "No Authorization"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "User not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PatchMapping("/{userId}/password")
  ResponseEntity<UserResponse> updateUserPassword(
      @PathVariable String userId,
      @Valid @RequestBody UserPasswordUpdateRequest request);

  @Operation(summary = "Update user status")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User status updated"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "401", description = "No Authorization"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "User not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PatchMapping("/{userId}/status")
  ResponseEntity<UserResponse> updateUserStatus(
      @PathVariable String userId,
      @Valid @RequestBody UserStatusUpdateRequest request);

  @Operation(summary = "Delete user")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "User deleted"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "401", description = "No Authorization"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "User not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/{userId}")
  ResponseEntity<Void> deleteUser(@PathVariable String userId);
}

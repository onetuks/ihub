package com.onetuks.ihub.controller.user;

import com.onetuks.ihub.dto.user.UserCreateRequest;
import com.onetuks.ihub.dto.user.UserResponse;
import com.onetuks.ihub.dto.user.UserUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/users")
@Tag(name = "User", description = "User management APIs")
public interface UserController {

  @Operation(summary = "Create user")
  @PostMapping
  ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request);

  @Operation(summary = "Get user by id")
  @GetMapping("/{userId}")
  ResponseEntity<UserResponse> getUser(@PathVariable Long userId);

  @Operation(summary = "List users")
  @GetMapping
  ResponseEntity<List<UserResponse>> getUsers();

  @Operation(summary = "Update user")
  @PutMapping("/{userId}")
  ResponseEntity<UserResponse> updateUser(
      @PathVariable Long userId,
      @Valid @RequestBody UserUpdateRequest request);

  @Operation(summary = "Delete user")
  @DeleteMapping("/{userId}")
  ResponseEntity<Void> deleteUser(@PathVariable Long userId);
}

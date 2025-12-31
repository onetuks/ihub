package com.onetuks.ihub.controller.role;

import static com.onetuks.ihub.config.RoleDataInitializer.USER_FULL_ACCESS;

import com.onetuks.ihub.annotation.RequiresRole;
import com.onetuks.ihub.dto.role.UserRoleRequest;
import com.onetuks.ihub.dto.role.UserRoleResponse;
import com.onetuks.ihub.entity.role.UserRole;
import com.onetuks.ihub.mapper.UserRoleMapper;
import com.onetuks.ihub.service.role.UserRoleSerivce;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRoleRestControllerImpl implements UserRoleRestController {

  private final UserRoleSerivce userRoleSerivce;

  @RequiresRole({USER_FULL_ACCESS})
  @Override
  public ResponseEntity<List<UserRoleResponse>> getAllUserRole(@PathVariable String userId) {
    List<UserRole> results = userRoleSerivce.getAll(userId);
    List<UserRoleResponse> response = results.stream().map(UserRoleMapper::toResponse).toList();
    return ResponseEntity.ok(response);
  }

  @RequiresRole({USER_FULL_ACCESS})
  @Override
  public ResponseEntity<List<UserRoleResponse>> updateUserRole(
      @PathVariable String userId, @Valid @RequestBody UserRoleRequest request) {
    List<UserRole> results = userRoleSerivce.update(userId, request);
    List<UserRoleResponse> response = results.stream().map(UserRoleMapper::toResponse).toList();
    return ResponseEntity.ok(response);
  }

  @RequiresRole({USER_FULL_ACCESS})
  @Override
  public ResponseEntity<UserRoleResponse> grantUserRole(
      @PathVariable String userId, @PathVariable String roleId) {
    UserRole result = userRoleSerivce.create(userId, roleId);
    UserRoleResponse response = UserRoleMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @RequiresRole({USER_FULL_ACCESS})
  @Override
  public ResponseEntity<UserRoleResponse> revokeUserRole(
      @PathVariable String userId, @PathVariable String roleId) {
    userRoleSerivce.delete(userId, roleId);
    return ResponseEntity.noContent().build();
  }
}

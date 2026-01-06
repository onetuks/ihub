package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.role.UserRoleResponse;
import com.onetuks.ihub.entity.role.UserRole;
import java.util.Objects;

public class UserRoleMapper {

  public static UserRoleResponse toResponse(UserRole userRole) {
    var user = Objects.requireNonNull(userRole.getUser());
    var role = Objects.requireNonNull(userRole.getRole());
    return new UserRoleResponse(
        userRole.getUserRoleId(),
        user.getUserId(),
        user.getStatus(),
        user.getName(),
        role.getRoleId()
    );
  }
}

package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.role.UserRoleResponse;
import com.onetuks.ihub.entity.role.UserRole;

public class UserRoleMapper {

  public static UserRoleResponse toResponse(UserRole userRole) {
    return new UserRoleResponse(
        userRole.getUserRoleId(),
        userRole.getUser().getUserId(),
        userRole.getRole().getRoleId()
    );
  }
}

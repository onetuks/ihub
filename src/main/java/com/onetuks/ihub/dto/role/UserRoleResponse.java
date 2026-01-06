package com.onetuks.ihub.dto.role;

import com.onetuks.ihub.entity.user.UserStatus;

public record UserRoleResponse(
    String userRoleId,
    String userId,
    UserStatus userStatus,
    String userName,
    String roleId
) {

}

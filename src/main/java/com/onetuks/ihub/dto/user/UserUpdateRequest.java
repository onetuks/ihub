package com.onetuks.ihub.dto.user;

import com.onetuks.ihub.entity.user.UserStatus;

public record UserUpdateRequest(
    String password,
    String name,
    String company,
    String position,
    String phoneNumber,
    String profileImageUrl,
    UserStatus status
) {

}

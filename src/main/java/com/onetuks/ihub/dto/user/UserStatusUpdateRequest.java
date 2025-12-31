package com.onetuks.ihub.dto.user;

import com.onetuks.ihub.entity.user.UserStatus;
import jakarta.validation.constraints.NotNull;

public record UserStatusUpdateRequest(
    @NotNull UserStatus status
) {

}

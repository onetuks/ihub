package com.onetuks.ihub.dto.user;

import jakarta.validation.constraints.NotNull;

public record UserPasswordUpdateRequest(
    @NotNull String password
) {

}

package com.onetuks.ihub.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(
    @Email @NotBlank String email,
    @NotBlank String name,
    String company,
    String position,
    String phoneNumber,
    String profileImageUrl
) {

  public static final String INITIAL_PASSWORD = "init1234";
}

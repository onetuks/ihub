package com.onetuks.ihub.dto.communication;

import jakarta.validation.constraints.NotBlank;

public record FaqAnswerUpdateRequest(
    @NotBlank String answer
) {

}

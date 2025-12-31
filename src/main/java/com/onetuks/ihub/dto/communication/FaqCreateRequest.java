package com.onetuks.ihub.dto.communication;

import jakarta.validation.constraints.NotBlank;

public record FaqCreateRequest(
    String category,
    @NotBlank String question,
    Boolean isSecret,
    String assigneeId
) {

}

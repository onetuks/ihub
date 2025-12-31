package com.onetuks.ihub.dto.communication;

public record FaqUpdateRequest(
    String category,
    String question,
    Boolean isSecret,
    String assigneeId
) {

}

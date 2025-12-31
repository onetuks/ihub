package com.onetuks.ihub.dto.communication;

import com.onetuks.ihub.entity.communication.FaqStatus;
import java.time.LocalDateTime;

public record FaqResponse(
    String faqId,
    String projectId,
    String category,
    String question,
    String answer,
    Boolean isSecret,
    String assigneeId,
    LocalDateTime answeredAt,
    FaqStatus status,
    String createdById,
    String updatedById,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime deletedAt
) {

}

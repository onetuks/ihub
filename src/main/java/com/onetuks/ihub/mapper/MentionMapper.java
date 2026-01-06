package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.communication.MentionCreateRequest;
import com.onetuks.ihub.dto.communication.MentionResponse;
import com.onetuks.ihub.entity.communication.Mention;
import java.time.LocalDateTime;
import java.util.Objects;

public final class MentionMapper {

  private MentionMapper() {
  }

  public static MentionResponse toResponse(Mention mention) {
    return new MentionResponse(
        mention.getMentionId(),
        Objects.requireNonNull(mention.getProject()).getProjectId(),
        Objects.requireNonNull(mention.getProject()).getStatus(),
        Objects.requireNonNull(mention.getProject()).getTitle(),
        mention.getTargetType(),
        mention.getTargetId(),
        Objects.requireNonNull(mention.getMentionedUser()).getUserId(),
        Objects.requireNonNull(mention.getMentionedUser()).getStatus(),
        Objects.requireNonNull(mention.getMentionedUser()).getName(),
        Objects.requireNonNull(mention.getCreatedBy()).getUserId(),
        Objects.requireNonNull(mention.getCreatedBy()).getStatus(),
        Objects.requireNonNull(mention.getCreatedBy()).getName(),
        mention.getCreatedAt());
  }

  public static void applyCreate(Mention mention, MentionCreateRequest request) {
    mention.setMentionId(UUIDProvider.provideUUID(Mention.TABLE_NAME));
    mention.setTargetType(request.targetType());
    mention.setTargetId(request.targetId());
    mention.setCreatedAt(LocalDateTime.now());
  }
}

package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.communication.FaqCreateRequest;
import com.onetuks.ihub.dto.communication.FaqResponse;
import com.onetuks.ihub.dto.communication.FaqUpdateRequest;
import com.onetuks.ihub.entity.communication.Faq;
import com.onetuks.ihub.entity.communication.FaqStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;
import java.util.Objects;

public final class FaqMapper {

  private FaqMapper() {
  }

  public static FaqResponse toResponse(Faq faq) {
    return new FaqResponse(
        faq.getFaqId(),
        Objects.requireNonNull(faq.getProject()).getProjectId(),
        Objects.requireNonNull(faq.getProject()).getStatus(),
        Objects.requireNonNull(faq.getProject()).getTitle(),
        faq.getCategory(),
        faq.getQuestion(),
        faq.getAnswer(),
        faq.getIsSecret(),
        Objects.requireNonNull(faq.getAssignee()).getUserId(),
        Objects.requireNonNull(faq.getAssignee()).getStatus(),
        Objects.requireNonNull(faq.getAssignee()).getName(),
        faq.getAnsweredAt(),
        faq.getStatus(),
        Objects.requireNonNull(faq.getCreatedBy()).getUserId(),
        Objects.requireNonNull(faq.getCreatedBy()).getStatus(),
        Objects.requireNonNull(faq.getCreatedBy()).getName(),
        Objects.requireNonNull(faq.getUpdatedBy()).getUserId(),
        Objects.requireNonNull(faq.getUpdatedBy()).getStatus(),
        Objects.requireNonNull(faq.getUpdatedBy()).getName(),
        faq.getCreatedAt(),
        faq.getUpdatedAt(),
        faq.getDeletedAt());
  }

  public static Faq applyCreate(
      User currentUser,
      Project project,
      FaqCreateRequest request
  ) {
    LocalDateTime now = LocalDateTime.now();
    Faq faq = new Faq();
    faq.setFaqId(UUIDProvider.provideUUID(Faq.TABLE_NAME));
    faq.setProject(project);
    faq.setCategory(request.category());
    faq.setQuestion(request.question());
    faq.setIsSecret(Boolean.TRUE.equals(request.isSecret()));
    faq.setStatus(FaqStatus.ACTIVE);
    faq.setCreatedBy(currentUser);
    faq.setUpdatedBy(currentUser);
    faq.setCreatedAt(now);
    faq.setUpdatedAt(now);
    return faq;
  }

  public static void applyUpdate(
      Faq faq,
      FaqUpdateRequest request,
      User updatedBy
  ) {
    if (request.category() != null) {
      faq.setCategory(request.category());
    }
    if (request.question() != null) {
      faq.setQuestion(request.question());
    }
    if (request.isSecret() != null) {
      faq.setIsSecret(request.isSecret());
    }
    faq.setUpdatedBy(updatedBy);
    faq.setUpdatedAt(LocalDateTime.now());
  }
}

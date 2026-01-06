package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.communication.NoticeCreateRequest;
import com.onetuks.ihub.dto.communication.NoticeResponse;
import com.onetuks.ihub.dto.communication.NoticeUpdateRequest;
import com.onetuks.ihub.entity.communication.Notice;
import com.onetuks.ihub.entity.communication.NoticeImportance;
import com.onetuks.ihub.entity.communication.NoticeStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;
import java.util.Objects;

public final class NoticeMapper {

  private NoticeMapper() {
  }

  public static NoticeResponse toResponse(Notice notice) {
    return new NoticeResponse(
        notice.getNoticeId(),
        Objects.requireNonNull(notice.getProject()).getProjectId(),
        Objects.requireNonNull(notice.getProject()).getStatus(),
        Objects.requireNonNull(notice.getProject()).getTitle(),
        notice.getTitle(),
        notice.getContent(),
        notice.getCategory(),
        notice.getImportance(),
        notice.getIsPinned(),
        notice.getPinnedAt(),
        notice.getStatus(),
        Objects.requireNonNull(notice.getCreatedBy()).getUserId(),
        Objects.requireNonNull(notice.getCreatedBy()).getStatus(),
        Objects.requireNonNull(notice.getCreatedBy()).getName(),
        Objects.requireNonNull(notice.getUpdatedBy()).getUserId(),
        Objects.requireNonNull(notice.getUpdatedBy()).getStatus(),
        Objects.requireNonNull(notice.getUpdatedBy()).getName(),
        notice.getCreatedAt(),
        notice.getUpdatedAt(),
        notice.getDeletedAt());
  }

  public static Notice applyCreate(
      User currentUser,
      Project project,
      NoticeCreateRequest request
  ) {
    LocalDateTime now = LocalDateTime.now();
    Notice notice = new Notice();
    notice.setNoticeId(UUIDProvider.provideUUID(Notice.TABLE_NAME));
    notice.setProject(project);
    notice.setTitle(request.title());
    notice.setContent(request.content());
    notice.setCategory(request.category());
    notice.setImportance(parseImportance(request.importance()));
    Boolean pinned = Boolean.TRUE.equals(request.isPinned());
    notice.setIsPinned(pinned);
    notice.setPinnedAt(pinned ? now : null);
    notice.setStatus(NoticeStatus.ACTIVE);
    notice.setCreatedBy(currentUser);
    notice.setUpdatedBy(currentUser);
    notice.setCreatedAt(now);
    notice.setUpdatedAt(now);
    return notice;
  }

  public static void applyUpdate(
      Notice notice,
      NoticeUpdateRequest request,
      User updatedBy
  ) {
    if (request.title() != null) {
      notice.setTitle(request.title());
    }
    if (request.content() != null) {
      notice.setContent(request.content());
    }
    if (request.category() != null) {
      notice.setCategory(request.category());
    }
    if (request.importance() != null) {
      notice.setImportance(parseImportance(request.importance()));
    }
    if (request.isPinned() != null) {
      notice.setIsPinned(request.isPinned());
      notice.setPinnedAt(request.isPinned() ? LocalDateTime.now() : null);
    }
    notice.setUpdatedBy(updatedBy);
    notice.setUpdatedAt(LocalDateTime.now());
  }

  private static NoticeImportance parseImportance(String importance) {
    if (importance == null || importance.isBlank()) {
      return NoticeImportance.NORMAL;
    }
    return NoticeImportance.valueOf(importance.toUpperCase());
  }
}

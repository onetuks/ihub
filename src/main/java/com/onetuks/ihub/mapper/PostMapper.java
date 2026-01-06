package com.onetuks.ihub.mapper;

import com.onetuks.ihub.dto.communication.PostCreateRequest;
import com.onetuks.ihub.dto.communication.PostResponse;
import com.onetuks.ihub.dto.communication.PostUpdateRequest;
import com.onetuks.ihub.entity.communication.Post;
import com.onetuks.ihub.entity.communication.PostStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import java.time.LocalDateTime;
import java.util.Objects;

public final class PostMapper {

  private PostMapper() {
  }

  public static PostResponse toResponse(Post post) {
    return new PostResponse(
        post.getPostId(),
        Objects.requireNonNull(post.getProject()).getProjectId(),
        Objects.requireNonNull(post.getProject()).getStatus(),
        Objects.requireNonNull(post.getProject()).getTitle(),
        post.getTitle(),
        post.getContent(),
        Objects.requireNonNull(post.getCreatedBy()).getUserId(),
        Objects.requireNonNull(post.getCreatedBy()).getStatus(),
        Objects.requireNonNull(post.getCreatedBy()).getName(),
        post.getCreatedAt(),
        post.getUpdatedAt());
  }

  public static Post applyCreate(User currentUser, Project project, PostCreateRequest request) {
    LocalDateTime now = LocalDateTime.now();
    return new Post(
        UUIDProvider.provideUUID(Post.TABLE_NAME),
        project,
        PostStatus.ACTIVE,
        request.title(),
        request.content(),
        currentUser,
        now,
        now
    );
  }

  public static void applyUpdate(Post post, PostUpdateRequest request) {
    if (request.title() != null) {
      post.setTitle(request.title());
    }
    if (request.content() != null) {
      post.setContent(request.content());
    }
    post.setUpdatedAt(LocalDateTime.now());
  }
}

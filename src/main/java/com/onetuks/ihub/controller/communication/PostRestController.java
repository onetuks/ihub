package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.PostCreateRequest;
import com.onetuks.ihub.dto.communication.PostResponse;
import com.onetuks.ihub.dto.communication.PostUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Tag(name = "Post", description = "Post management APIs")
public interface PostRestController {

  @Operation(summary = "List posts")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Posts listed"),
      @ApiResponse(responseCode = "401", description = "UnAuthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/api/projects/{projectId}/posts")
  ResponseEntity<Page<PostResponse>> getPosts(
      @PathVariable String projectId,
      @PageableDefault Pageable pageable);

  @Operation(summary = "Create post")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "Post created"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "401", description = "UnAuthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping("/api/projects/{projectId}/posts")
  ResponseEntity<PostResponse> createPost(
      @PathVariable String projectId,
      @Valid @RequestBody PostCreateRequest request);

  @Operation(summary = "Get post by id")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Post found"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "401", description = "UnAuthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "Post not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/api/posts/{postId}")
  ResponseEntity<PostResponse> getPost(@PathVariable String postId);

  @Operation(summary = "Update post")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Post updated"),
      @ApiResponse(responseCode = "400", description = "Invalid request"),
      @ApiResponse(responseCode = "401", description = "UnAuthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "Post not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PatchMapping("/api/posts/{postId}")
  ResponseEntity<PostResponse> updatePost(
      @PathVariable String postId,
      @Valid @RequestBody PostUpdateRequest request);

  @Operation(summary = "Delete post")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Post deleted"),
      @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
      @ApiResponse(responseCode = "401", description = "UnAuthorized"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "404", description = "Post not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/api/posts/{postId}")
  ResponseEntity<Void> deletePost(@PathVariable String postId);
}

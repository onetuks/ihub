package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.CommentCreateRequest;
import com.onetuks.ihub.dto.communication.CommentCreateResponse;
import com.onetuks.ihub.dto.communication.CommentResponse;
import com.onetuks.ihub.dto.communication.CommentUpdateRequest;
import com.onetuks.ihub.entity.communication.TargetType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Tag(name = "Comment", description = "댓글 관리 API")
public interface CommentRestController {

  @Operation(summary = "댓글 생성", description = "타겟 리소스에 댓글을 등록합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "댓글 생성 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "대상을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @PostMapping("/api/{targetType}/{targetId}/comments")
  ResponseEntity<CommentCreateResponse> createComment(
      @PathVariable TargetType targetType,
      @PathVariable String targetId,
      @Valid @RequestBody CommentCreateRequest request
  );

  @Operation(summary = "댓글 목록 조회", description = "타겟 리소스에 등록된 댓글을 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "댓글 조회 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "대상을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @GetMapping("/api/{targetType}/{targetId}/comments")
  ResponseEntity<List<CommentResponse>> getComments(
      @PathVariable TargetType targetType,
      @PathVariable String targetId
  );

  @Operation(summary = "댓글 수정", description = "댓글 내용을 수정합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "댓글 수정 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @PatchMapping("/api/comments/{commentId}")
  ResponseEntity<CommentResponse> updateComment(
      @PathVariable String commentId,
      @Valid @RequestBody CommentUpdateRequest request
  );

  @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "댓글 삭제 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @DeleteMapping("/api/comments/{commentId}")
  ResponseEntity<Void> deleteComment(
      @PathVariable String commentId
  );
}

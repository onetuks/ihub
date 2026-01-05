package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.FaqAnswerUpdateRequest;
import com.onetuks.ihub.dto.communication.FaqCreateRequest;
import com.onetuks.ihub.dto.communication.FaqResponse;
import com.onetuks.ihub.dto.communication.FaqUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping
@Tag(name = "Faq", description = "FAQ 관리 API")
public interface FaqRestController {

  @Operation(summary = "FAQ 목록 조회", description = "프로젝트 내 FAQ 목록을 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "FAQ 조회 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "대상을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @GetMapping("/api/projects/{projectId}/faqs")
  ResponseEntity<Page<FaqResponse>> getFaqs(
      @PathVariable String projectId,
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String answerStatus,
      @RequestParam(required = false) String assigneeId,
      @RequestParam(required = false) Boolean isSecret,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
      @PageableDefault Pageable pageable
  );

  @Operation(summary = "FAQ 생성", description = "프로젝트 내 FAQ를 생성합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "FAQ 생성 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "대상을 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @PostMapping("/api/projects/{projectId}/faqs")
  ResponseEntity<FaqResponse> createFaq(
      @PathVariable String projectId,
      @Valid @RequestBody FaqCreateRequest request
  );

  @Operation(summary = "FAQ 상세 조회", description = "FAQ 상세 정보를 조회합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "FAQ 조회 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "FAQ를 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @GetMapping("/api/faqs/{faqId}")
  ResponseEntity<FaqResponse> getFaq(
      @PathVariable String faqId
  );

  @Operation(summary = "FAQ 수정", description = "FAQ의 질문, 카테고리, 비밀글 여부, 담당자를 수정합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "FAQ 수정 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "FAQ를 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @PatchMapping("/api/faqs/{faqId}")
  ResponseEntity<FaqResponse> updateFaq(
      @PathVariable String faqId,
      @Valid @RequestBody FaqUpdateRequest request
  );

  @Operation(summary = "FAQ 답변 등록", description = "FAQ의 답변을 등록합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "FAQ 답변 등록 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "FAQ를 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @PatchMapping("/api/faqs/{faqId}/answer")
  ResponseEntity<FaqResponse> answerFaq(
      @PathVariable String faqId,
      @Valid @RequestBody FaqAnswerUpdateRequest request
  );

  @Operation(summary = "FAQ 삭제", description = "FAQ를 삭제합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "FAQ 삭제 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
      @ApiResponse(responseCode = "401", description = "인증이 필요합니다."),
      @ApiResponse(responseCode = "403", description = "권한이 없습니다."),
      @ApiResponse(responseCode = "404", description = "FAQ를 찾을 수 없습니다."),
      @ApiResponse(responseCode = "500", description = "서버 오류")
  })
  @DeleteMapping("/api/faqs/{faqId}")
  ResponseEntity<Void> deleteFaq(
      @PathVariable String faqId
  );
}

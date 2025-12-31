package com.onetuks.ihub.controller.communication;

import com.onetuks.ihub.dto.communication.FaqAnswerUpdateRequest;
import com.onetuks.ihub.dto.communication.FaqCreateRequest;
import com.onetuks.ihub.dto.communication.FaqResponse;
import com.onetuks.ihub.dto.communication.FaqUpdateRequest;
import com.onetuks.ihub.entity.communication.Faq;
import com.onetuks.ihub.mapper.FaqMapper;
import com.onetuks.ihub.security.CurrentUserProvider;
import com.onetuks.ihub.service.communication.FaqService;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FaqRestControllerImpl implements FaqRestController {

  private final CurrentUserProvider currentUserProvider;
  private final FaqService faqService;

  @Override
  public ResponseEntity<Page<FaqResponse>> getFaqs(
      @PathVariable String projectId,
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) String category,
      @RequestParam(required = false) String answerStatus,
      @RequestParam(required = false) String assigneeId,
      @RequestParam(required = false) Boolean isSecret,
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
      LocalDateTime from,
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
      LocalDateTime to,
      @PageableDefault Pageable pageable
  ) {
    Page<Faq> results = faqService.getAll(
        currentUserProvider.resolveUser(),
        projectId,
        keyword,
        category,
        answerStatus,
        assigneeId,
        isSecret,
        from,
        to,
        pageable
    );
    Page<FaqResponse> responses = results.map(FaqMapper::toResponse);
    return ResponseEntity.ok(responses);
  }

  @Override
  public ResponseEntity<FaqResponse> createFaq(
      @PathVariable String projectId,
      @Valid @RequestBody FaqCreateRequest request
  ) {
    Faq result = faqService.create(currentUserProvider.resolveUser(), projectId, request);
    FaqResponse response = FaqMapper.toResponse(result);
    return ResponseEntity.created(URI.create("/api/faqs/" + response.faqId())).body(response);
  }

  @Override
  public ResponseEntity<FaqResponse> getFaq(@PathVariable String faqId) {
    Faq result = faqService.getById(currentUserProvider.resolveUser(), faqId);
    FaqResponse response = FaqMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<FaqResponse> updateFaq(
      @PathVariable String faqId,
      @Valid @RequestBody FaqUpdateRequest request
  ) {
    Faq result = faqService.update(currentUserProvider.resolveUser(), faqId, request);
    FaqResponse response = FaqMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<FaqResponse> answerFaq(
      @PathVariable String faqId,
      @Valid @RequestBody FaqAnswerUpdateRequest request
  ) {
    Faq result = faqService.answer(currentUserProvider.resolveUser(), faqId, request);
    FaqResponse response = FaqMapper.toResponse(result);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> deleteFaq(@PathVariable String faqId) {
    faqService.delete(currentUserProvider.resolveUser(), faqId);
    return ResponseEntity.noContent().build();
  }
}

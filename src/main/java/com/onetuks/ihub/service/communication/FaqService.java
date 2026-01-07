package com.onetuks.ihub.service.communication;

import com.onetuks.ihub.dto.communication.FaqAnswerUpdateRequest;
import com.onetuks.ihub.dto.communication.FaqCreateRequest;
import com.onetuks.ihub.dto.communication.FaqUpdateRequest;
import com.onetuks.ihub.entity.communication.Faq;
import com.onetuks.ihub.entity.communication.FaqStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.FaqMapper;
import com.onetuks.ihub.repository.FaqJpaRepository;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.project.ProjectMemberCheckComponent;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FaqService {

  private final ProjectMemberCheckComponent projectMemberCheckComponent;
  private final FaqJpaRepository faqJpaRepository;
  private final ProjectJpaRepository projectJpaRepository;
  private final UserJpaRepository userJpaRepository;

  @Transactional
  public Faq create(User currentUser, String projectId, FaqCreateRequest request) {
    Project project = findProject(projectId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser.getUserId(), projectId);

    Faq faq = FaqMapper.applyCreate(currentUser, project, request);
    if (request.assigneeId() != null) {
      faq.setAssignee(findUser(request.assigneeId()));
    }
    return faqJpaRepository.save(faq);
  }

  @Transactional(readOnly = true)
  public Faq getById(User currentUser, String faqId) {
    Faq faq = findEntity(faqId);
    projectMemberCheckComponent.checkIsProjectViewer(
        currentUser.getUserId(), faq.getProject().getProjectId());
    return faq;
  }

  @Transactional(readOnly = true)
  public Page<Faq> getAll(
      User currentUser,
      String projectId,
      String keyword,
      String category,
      String answerStatus,
      String assigneeId,
      Boolean isSecret,
      LocalDateTime from,
      LocalDateTime to,
      Pageable pageable
  ) {
    projectMemberCheckComponent.checkIsProjectViewer(currentUser.getUserId(), projectId);

    List<Faq> faqs = faqJpaRepository.findAllByProject_ProjectIdAndStatusNot(projectId,
        FaqStatus.DELETED);
    List<Faq> filtered = faqs.stream()
        .filter(faq -> keyword == null || keyword.isBlank()
            || containsIgnoreCase(faq.getQuestion(), keyword)
            || containsIgnoreCase(faq.getAnswer(), keyword))
        .filter(faq -> category == null || Objects.equals(category, faq.getCategory()))
        .filter(faq -> answerStatus == null
            || ("ANSWERED".equalsIgnoreCase(answerStatus) && faq.getAnswer() != null)
            || ("UNANSWERED".equalsIgnoreCase(answerStatus) && faq.getAnswer() == null))
        .filter(faq -> assigneeId == null
            || (faq.getAssignee() != null && Objects.equals(assigneeId,
            faq.getAssignee().getUserId())))
        .filter(faq -> isSecret == null || Objects.equals(isSecret, faq.getIsSecret()))
        .filter(faq -> from == null
            || (faq.getCreatedAt() != null && !faq.getCreatedAt().isBefore(from)))
        .filter(faq -> to == null
            || (faq.getCreatedAt() != null && !faq.getCreatedAt().isAfter(to)))
        .toList();

    List<Faq> pageContent = slice(filtered, pageable);
    return new PageImpl<>(pageContent, pageable, filtered.size());
  }

  @Transactional
  public Faq update(User currentUser, String faqId, FaqUpdateRequest request) {
    Faq faq = findEntity(faqId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser.getUserId(),
        faq.getProject().getProjectId());
    FaqMapper.applyUpdate(faq, request, currentUser);
    if (request.assigneeId() != null) {
      faq.setAssignee(findUser(request.assigneeId()));
    }
    return faq;
  }

  @Transactional
  public Faq answer(User currentUser, String faqId, FaqAnswerUpdateRequest request) {
    Faq faq = findEntity(faqId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser.getUserId(),
        faq.getProject().getProjectId());
    faq.setAnswer(request.answer());
    faq.setAnsweredAt(LocalDateTime.now());
    faq.setUpdatedBy(currentUser);
    faq.setUpdatedAt(LocalDateTime.now());
    return faq;
  }

  @Transactional
  public Faq delete(User currentUser, String faqId) {
    Faq faq = findEntity(faqId);
    projectMemberCheckComponent.checkIsProjectMember(currentUser.getUserId(),
        faq.getProject().getProjectId());
    faq.setStatus(FaqStatus.DELETED);
    faq.setDeletedAt(LocalDateTime.now());
    faq.setUpdatedBy(currentUser);
    faq.setUpdatedAt(LocalDateTime.now());
    return faq;
  }

  private Faq findEntity(String faqId) {
    return faqJpaRepository.findById(faqId)
        .orElseThrow(() -> new EntityNotFoundException("Faq not found: " + faqId));
  }

  private Project findProject(String projectId) {
    return projectJpaRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectId));
  }

  private User findUser(String userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
  }

  private boolean containsIgnoreCase(String text, String keyword) {
    return text != null && text.toLowerCase().contains(keyword.toLowerCase());
  }

  private List<Faq> slice(List<Faq> faqs, Pageable pageable) {
    int start = (int) pageable.getOffset();
    if (start >= faqs.size()) {
      return List.of();
    }
    int end = Math.min(start + pageable.getPageSize(), faqs.size());
    return faqs.subList(start, end);
  }
}

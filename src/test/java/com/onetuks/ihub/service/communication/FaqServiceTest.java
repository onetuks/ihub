package com.onetuks.ihub.service.communication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.communication.FaqAnswerUpdateRequest;
import com.onetuks.ihub.dto.communication.FaqCreateRequest;
import com.onetuks.ihub.dto.communication.FaqUpdateRequest;
import com.onetuks.ihub.entity.communication.Faq;
import com.onetuks.ihub.entity.communication.FaqStatus;
import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.exception.AccessDeniedException;
import com.onetuks.ihub.repository.ProjectJpaRepository;
import com.onetuks.ihub.repository.ProjectMemberJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class FaqServiceTest {

  @Autowired
  private FaqService faqService;

  @Autowired
  private ProjectJpaRepository projectJpaRepository;

  @Autowired
  private ProjectMemberJpaRepository projectMemberJpaRepository;

  @Autowired
  private UserJpaRepository userJpaRepository;

  private Project project;
  private User author;
  private User assignee;

  @BeforeEach
  void setUp() {
    author = ServiceTestDataFactory.createUser(userJpaRepository);
    assignee = ServiceTestDataFactory.createUser(userJpaRepository);
    project = ServiceTestDataFactory.createProject(projectJpaRepository, author, author, "FaqProj");
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, author);
    ServiceTestDataFactory.createProjectMember(projectMemberJpaRepository, project, assignee);
  }

  @Test
  void createFaq_success() {
    FaqCreateRequest request = new FaqCreateRequest("category", "question", true, assignee.getUserId());

    Faq result = faqService.create(author, project.getProjectId(), request);

    assertThat(result.getFaqId()).isNotNull();
    assertThat(result.getQuestion()).isEqualTo("question");
    assertThat(result.getAssignee().getUserId()).isEqualTo(assignee.getUserId());
    assertThat(result.getIsSecret()).isTrue();
  }

  @Test
  void getFaqs_filtersAndRespectsPagination() {
    faqService.create(author, project.getProjectId(), new FaqCreateRequest("cat", "q1", false, null));
    faqService.create(author, project.getProjectId(), new FaqCreateRequest("cat", "another", false, null));
    Pageable pageable = PageRequest.of(0, 1);

    Page<Faq> page = faqService.getAll(
        author,
        project.getProjectId(),
        "q1",
        "cat",
        null,
        null,
        false,
        null,
        null,
        pageable);

    assertThat(page.getTotalElements()).isEqualTo(1);
    assertThat(page.getContent().get(0).getQuestion()).isEqualTo("q1");
  }

  @Test
  void updateFaq_success() {
    Faq created = faqService.create(author, project.getProjectId(), new FaqCreateRequest("c", "q", false, null));
    FaqUpdateRequest update = new FaqUpdateRequest("newCat", "newQ", true, assignee.getUserId());

    Faq updated = faqService.update(author, created.getFaqId(), update);

    assertThat(updated.getCategory()).isEqualTo("newCat");
    assertThat(updated.getQuestion()).isEqualTo("newQ");
    assertThat(updated.getIsSecret()).isTrue();
    assertThat(updated.getAssignee().getUserId()).isEqualTo(assignee.getUserId());
  }

  @Test
  void answerFaq_success() {
    Faq created = faqService.create(author, project.getProjectId(), new FaqCreateRequest("c", "q", false, null));
    FaqAnswerUpdateRequest answerRequest = new FaqAnswerUpdateRequest("answer");

    Faq answered = faqService.answer(author, created.getFaqId(), answerRequest);

    assertThat(answered.getAnswer()).isEqualTo("answer");
    assertThat(answered.getAnsweredAt()).isNotNull();
  }

  @Test
  void deleteFaq_marksDeleted() {
    Faq created = faqService.create(author, project.getProjectId(), new FaqCreateRequest("c", "q", false, null));

    Faq deleted = faqService.delete(author, created.getFaqId());

    assertThat(deleted.getStatus()).isEqualTo(FaqStatus.DELETED);
    assertThat(deleted.getDeletedAt()).isNotNull();
  }

  @Test
  void accessDeniedForNonMember() {
    User outsider = ServiceTestDataFactory.createUser(userJpaRepository);
    Faq created = faqService.create(author, project.getProjectId(), new FaqCreateRequest("c", "q", false, null));

    assertThatThrownBy(() -> faqService.getById(outsider, created.getFaqId()))
        .isInstanceOf(AccessDeniedException.class);
  }
}

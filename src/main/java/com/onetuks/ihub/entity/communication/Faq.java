package com.onetuks.ihub.entity.communication;

import com.onetuks.ihub.entity.project.Project;
import com.onetuks.ihub.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Faq.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Faq {

  public static final String TABLE_NAME = "faqs";

  @Id
  @Column(name = "faq_id", nullable = false)
  private String faqId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false)
  private Project project;

  @Column(name = "category")
  private String category;

  @Column(name = "question", nullable = false, length = 500)
  private String question;

  @Column(name = "answer")
  private String answer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assignee_id", referencedColumnName = "user_id")
  private User assignee;

  @Column(name = "answered_at")
  private LocalDateTime answeredAt;

  @Column(name = "is_secret", nullable = false)
  private Boolean isSecret;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private FaqStatus status;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "created_by", referencedColumnName = "user_id", nullable = false)
  private User createdBy;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "updated_by", referencedColumnName = "user_id", nullable = false)
  private User updatedBy;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;
}

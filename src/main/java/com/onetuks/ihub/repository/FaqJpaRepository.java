package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.communication.Faq;
import com.onetuks.ihub.entity.communication.FaqStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqJpaRepository extends JpaRepository<Faq, String> {

  List<Faq> findAllByProject_ProjectIdAndStatusNot(String projectId, FaqStatus status);
}

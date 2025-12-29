package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.communication.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventJpaRepository extends JpaRepository<Event, String> {

  Page<Event> findAllByProject_ProjectId(String projectId, Pageable pageable);
}

package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.communication.Comment;
import com.onetuks.ihub.entity.communication.TargetType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, String> {

  List<Comment> findAllByTargetTypeAndTargetIdOrderByCreatedAtAsc(TargetType targetType, String targetId);
}

package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.project.ProjectMember;
import com.onetuks.ihub.entity.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMemberJpaRepository extends JpaRepository<ProjectMember, String> {

  Page<ProjectMember> findAllByUser(User user, Pageable pageable);

  List<ProjectMember> user(User user);

  Optional<ProjectMember> findByProject_ProjectIdAndUser_UserId(String projectId, String userId);
}

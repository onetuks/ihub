package com.onetuks.ihub.repository;

import com.onetuks.ihub.entity.role.Role;
import com.onetuks.ihub.entity.role.UserRole;
import com.onetuks.ihub.entity.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleJpaRepository extends JpaRepository<UserRole, String> {

  List<UserRole> findAllByUser(User user);

  void deleteAllByRole_RoleId(String roleRoleId);

  List<UserRole> findAllByUser_UserId(String userId);

  void deleteAllByUser_UserId(String userId);

  void deleteByUser_UserIdAndRole_RoleId(String userId, String roleId);
}

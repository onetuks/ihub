package com.onetuks.ihub.service.role;

import com.onetuks.ihub.dto.role.UserRoleRequest;
import com.onetuks.ihub.entity.role.Role;
import com.onetuks.ihub.entity.role.UserRole;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.UUIDProvider;
import com.onetuks.ihub.repository.RoleJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.repository.UserRoleJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRoleSerivce {

  private final UserRoleJpaRepository userRoleJpaRepository;
  private final UserJpaRepository userJpaRepository;
  private final RoleJpaRepository roleJpaRepository;

  @Transactional(readOnly = true)
  public List<UserRole> getAll(String userId) {
    return userRoleJpaRepository.findAllByUser_UserId(userId);
  }

  @Transactional
  public List<UserRole> update(String userId, UserRoleRequest request) {
    userRoleJpaRepository.deleteAllByUser_UserId(userId);
    return request.roleIds().stream()
        .map(roleId -> new UserRole(
            UUIDProvider.provideUUID(UserRole.TABLE_NAME),
            findUser(userId),
            findRole(roleId)))
        .map(userRoleJpaRepository::save)
        .toList();
  }

  @Transactional
  public UserRole create(String userId, String roleId) {
    return userRoleJpaRepository.save(
        new UserRole(
            UUIDProvider.provideUUID(UserRole.TABLE_NAME),
            findUser(userId),
            findRole(roleId)));
  }

  @Transactional
  public void delete(String userId, String roleId) {
    userRoleJpaRepository.deleteByUser_UserIdAndRole_RoleId(userId, roleId);
  }

  private User findUser(String userId) {
    return userJpaRepository.findById(userId)
        .orElseThrow(EntityNotFoundException::new);
  }

  private Role findRole(String roleId) {
    return roleJpaRepository.findById(roleId)
        .orElseThrow(EntityNotFoundException::new);
  }
}

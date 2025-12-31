package com.onetuks.ihub.service.role;

import static org.assertj.core.api.Assertions.assertThat;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.role.UserRoleRequest;
import com.onetuks.ihub.entity.role.Role;
import com.onetuks.ihub.entity.role.UserRole;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.repository.RoleJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.repository.UserRoleJpaRepository;
import com.onetuks.ihub.service.ServiceTestDataFactory;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class UserRoleSerivceTest {

  @Autowired
  private UserRoleSerivce userRoleSerivce;

  @Autowired
  private UserRoleJpaRepository userRoleJpaRepository;
  @Autowired
  private UserJpaRepository userJpaRepository;
  @Autowired
  private RoleJpaRepository roleJpaRepository;

  private User user;
  private List<Role> roles;

  @BeforeEach
  void setUp() {
    user = ServiceTestDataFactory.createUser(userJpaRepository);
    roles = ServiceTestDataFactory.createRoles(roleJpaRepository);
  }

  @Test
  void getAll_success() {
    // Given
    long expected = userRoleJpaRepository.count() + 1;
    userRoleSerivce.create(user.getUserId(), roles.getFirst().getRoleId());

    // When
    List<UserRole> results = userRoleSerivce.getAll(user.getUserId());

    // Then
    assertThat(results).hasSize((int) expected);
  }

  @Test
  void update_success() {
    // Given
    UserRoleRequest request = new UserRoleRequest(roles.stream().map(Role::getRoleId).toList());

    // When
    List<UserRole> results = userRoleSerivce.update(user.getUserId(), request);

    // Then
    assertThat(results).hasSize(roles.size());
  }

  @Test
  void create_success() {
    // When
    UserRole result = userRoleSerivce.create(user.getUserId(), roles.getLast().getRoleId());

    // Then
    assertThat(result.getRole().getRoleName()).isEqualToIgnoringCase(roles.getLast().getRoleName());
  }

  @Test
  void delete_success() {
    // Given
    UserRole created = userRoleSerivce.create(user.getUserId(), roles.getLast().getRoleId());

    // When
    userRoleSerivce.delete(user.getUserId(), roles.getLast().getRoleId());

    // Then
    boolean result = userRoleJpaRepository.findById(created.getUserRoleId()).isEmpty();
    assertThat(result).isTrue();
  }
}
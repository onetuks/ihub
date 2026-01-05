package com.onetuks.ihub.service.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.user.UserCreateRequest;
import com.onetuks.ihub.dto.user.UserResponse;
import com.onetuks.ihub.dto.user.UserUpdateRequest;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.entity.user.UserStatus;
import com.onetuks.ihub.mapper.UserMapper;
import com.onetuks.ihub.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class UserServiceTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserJpaRepository userJpaRepository;

  @Test
  void createUser_success() {
    UserCreateRequest request = buildCreateRequest("user1@example.com", "User One");

    UserResponse response = UserMapper.toResponse(userService.create(request));

    assertNotNull(response.email());
    assertEquals("user1@example.com", response.email());
    assertEquals("User One", response.name());
    assertEquals(UserStatus.INACTIVE, response.status());
    assertNotNull(response.createdAt());
    assertNotNull(response.updatedAt());
  }

  @Test
  void updateUser_success() {
    UserResponse created = UserMapper.toResponse(
        userService.create(buildCreateRequest("user2@example.com", "User Two")));
    UserUpdateRequest updateRequest = new UserUpdateRequest(
        "User Two Updated",
        "NewCo",
        "Lead",
        "010-0000-0000",
        "http://example.com/img.png",
        UserStatus.INACTIVE);

    UserResponse updated = UserMapper.toResponse(
        userService.update(created.email(), updateRequest));

    assertEquals("user2@example.com", updated.email());
    assertEquals("User Two Updated", updated.name());
    assertEquals("NewCo", updated.company());
    assertEquals("Lead", updated.position());
    assertEquals("010-0000-0000", updated.phoneNumber());
    assertEquals("http://example.com/img.png", updated.profileImageUrl());
    assertEquals(UserStatus.INACTIVE, updated.status());
  }

  @Test
  void getAllByName_success() {
    // Given
    String name = "Mr.example";
    Pageable pageable = PageRequest.of(0, 10);
    userService.create(buildCreateRequest("example@example.com", name));

    // When
    Page<User> results = userService.getAllByName(name.substring(0, 3), pageable);

    // Then
    assertThat(results.getTotalElements()).isEqualTo(1);
  }

  @Test
  void getUsers_returnsAll() {
    long expected = userJpaRepository.count() + 2;
    Pageable pageable = PageRequest.of(10, 10);
    userService.create(buildCreateRequest("a@example.com", "A"));
    userService.create(buildCreateRequest("b@example.com", "B"));

    Page<User> results = userService.getAll(pageable);

    assertThat(results.getTotalElements()).isEqualTo(expected);
  }

  @Test
  void deleteUser_success() {
    UserResponse created = UserMapper.toResponse(userService.create(
        buildCreateRequest("user3@example.com", "User Three")));

    User result = userService.delete(created.email());

    assertThat(result.getEmail()).isEqualTo(created.email());
    assertThat(result.getStatus()).isEqualTo(UserStatus.DELETED);
  }

  private UserCreateRequest buildCreateRequest(String email, String name) {
    return new UserCreateRequest(
        email,
        name,
        "Company",
        "Position",
        "010-1234-5678",
        "http://example.com/profile.png");
  }
}

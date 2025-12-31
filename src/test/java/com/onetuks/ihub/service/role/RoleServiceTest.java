package com.onetuks.ihub.service.role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.onetuks.ihub.TestcontainersConfiguration;
import com.onetuks.ihub.dto.role.RoleCreateRequest;
import com.onetuks.ihub.dto.role.RoleResponse;
import com.onetuks.ihub.dto.role.RoleUpdateRequest;
import com.onetuks.ihub.entity.role.Role;
import com.onetuks.ihub.mapper.RoleMapper;
import com.onetuks.ihub.repository.RoleJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class RoleServiceTest {

  @Autowired
  private RoleService roleService;

  @Autowired
  private RoleJpaRepository roleJpaRepository;

  @Test
  void createRole_success() {
    RoleCreateRequest request = new RoleCreateRequest("Admin", "Administrator role");

    RoleResponse response = RoleMapper.toResponse(roleService.create(request));

    assertNotNull(response.roleId());
    assertEquals("Admin", response.roleName());
    assertEquals("Administrator role", response.description());
  }

  @Test
  void updateRole_success() {
    RoleResponse created = RoleMapper.toResponse(roleService.create(
        new RoleCreateRequest("Member", "Member role")));
    RoleUpdateRequest updateRequest = new RoleUpdateRequest("Member Updated", "Updated desc");

    RoleResponse updated = RoleMapper.toResponse(
        roleService.update(created.roleId(), updateRequest));

    assertEquals("Member Updated", updated.roleName());
    assertEquals("Updated desc", updated.description());
  }

  @Test
  void getRoles_returnsAll() {
    long expected = roleJpaRepository.count() + 2;
    roleService.create(new RoleCreateRequest("R1", "Role one"));
    roleService.create(new RoleCreateRequest("R2", "Role two"));

    List<RoleResponse> responses = roleService.getAll().stream()
        .map(RoleMapper::toResponse)
        .toList();

    assertEquals(expected, responses.size());
  }

  @Test
  void deleteRole_success() {
    // Given
    long expected = roleJpaRepository.count();
    Role role = roleService.create(new RoleCreateRequest("Temp", "Temp role"));

    roleService.delete(role.getRoleId());

    assertEquals(expected, roleJpaRepository.count());
    assertThrows(EntityNotFoundException.class, () -> roleService.getById(role.getRoleId()));
  }
}

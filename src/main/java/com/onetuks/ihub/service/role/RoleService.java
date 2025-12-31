package com.onetuks.ihub.service.role;

import com.onetuks.ihub.dto.role.RoleCreateRequest;
import com.onetuks.ihub.dto.role.RoleUpdateRequest;
import com.onetuks.ihub.entity.role.Role;
import com.onetuks.ihub.entity.role.UserRole;
import com.onetuks.ihub.entity.user.User;
import com.onetuks.ihub.mapper.RoleMapper;
import com.onetuks.ihub.mapper.UUIDProvider;
import com.onetuks.ihub.repository.RoleJpaRepository;
import com.onetuks.ihub.repository.UserJpaRepository;
import com.onetuks.ihub.repository.UserRoleJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleJpaRepository roleRepository;
  private final UserRoleJpaRepository userRoleRepository;

  @Transactional
  public Role create(RoleCreateRequest request) {
    return roleRepository.save(RoleMapper.applyCreate(request));
  }

  @Transactional(readOnly = true)
  public Role getById(String roleId) {
    return findEntity(roleId);
  }

  @Transactional(readOnly = true)
  public List<Role> getAll() {
    return roleRepository.findAll();
  }

  @Transactional
  public Role update(String roleId, RoleUpdateRequest request) {
    return RoleMapper.applyUpdate(findEntity(roleId), request);
  }

  @Transactional
  public void delete(String roleId) {
    userRoleRepository.deleteAllByRole_RoleId(roleId);
    roleRepository.deleteById(roleId);
  }

  private Role findEntity(String roleId) {
    return roleRepository.findById(roleId)
        .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));
  }
}

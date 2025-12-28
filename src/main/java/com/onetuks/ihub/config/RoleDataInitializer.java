package com.onetuks.ihub.config;

import com.onetuks.ihub.entity.role.Role;
import com.onetuks.ihub.mapper.UUIDProvider;
import com.onetuks.ihub.repository.RoleJpaRepository;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RoleDataInitializer implements ApplicationRunner {

  public static final String USER_FULL_ACCESS = "USER_FULL_ACCESS";
  public static final String PROJECT_FULL_ACCESS = "PROJECT_FULL_ACCESS";
  public static final String SYSTEM_FULL_ACCESS = "SYSTEM_FULL_ACCESS";
  public static final String TASK_FULL_ACCESS = "TASK_FULL_ACCESS";
  public static final String POST_FULL_ACCESS = "POST_FULL_ACCESS";
  public static final Map<String, String> PREPARED_ROLES = Map.of(
      USER_FULL_ACCESS, "계정을 생성하고 수정할 수 있습니다.\n계정에 권한을 부여하고 수정할 수 있습니다.",
      PROJECT_FULL_ACCESS, "프로젝트를 생성 및 수정할 수 있습니다.\n프로젝트에 초대되는 멤버의 권한(ADMIN, MEMBER, VIEWER)을 관리할 수 있습니다.\n프로젝트에 초대된 멤버를 삭제할 수 있습니다.",
      SYSTEM_FULL_ACCESS, "프로젝트 내에서 시스템을 생성하고 수정하고, 삭제할 수 있습니다.\n프로젝트 내에서 시스템 별로 접속 정보를 관리할 담당자를 지정할 수 있습니다.",
      TASK_FULL_ACCESS, "프로젝트 내 전체 일감에 대한 리스트 및 상세 내역을 조회할 수 있습니다.",
      POST_FULL_ACCESS, "프로젝트 내 메모를 작성하고, 조회할 수 있습니다.\n프로젝트 내 메모란은 EAI 개발자들을 위해 만들어진 공유 공간입니다."
  );

  private final RoleJpaRepository roleJpaRepository;

  @Override
  @Transactional
  public void run(ApplicationArguments args) {
    List<Role> allRoles = roleJpaRepository.findAll();
    PREPARED_ROLES.entrySet().stream()
        .filter(entrySet -> allRoles.stream()
            .noneMatch(role -> Objects.equals(role.getRoleName(), entrySet.getKey())))
        .forEach(
            entrySet -> roleJpaRepository.save(createRole(entrySet.getKey(), entrySet.getValue())));
  }

  private Role createRole(String roleName, String description) {
    Role role = new Role();
    role.setRoleId(UUIDProvider.provideUUID(Role.TABLE_NAME));
    role.setRoleName(roleName);
    role.setDescription(description);
    return role;
  }
}

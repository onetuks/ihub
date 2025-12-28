package com.onetuks.ihub.config;

import com.onetuks.ihub.annotation.RequiresRole;
import com.onetuks.ihub.exception.AccessDeniedException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleAuthorizationAspect {

  @Around("@annotation(com.onetuks.ihub.annotation.RequiresRole)"
      + " || @within(com.onetuks.ihub.annotation.RequiresRole)")
  public Object checkAccess(ProceedingJoinPoint joinPoint) throws Throwable {
    RequiresRole requiresRole = resolveAnnotation(joinPoint);
    if (requiresRole == null || requiresRole.value().length == 0) {
      return joinPoint.proceed();
    }

    Set<String> required = Set.of(requiresRole.value());
    List<String> userRoles = resolveCurrentUserRoles();

    boolean allowed = userRoles.stream().anyMatch(required::contains);
    if (!allowed) {
      throw new AccessDeniedException(
          "User lacks required role(s): " + String.join(", ", required));
    }

    return joinPoint.proceed();
  }

  private RequiresRole resolveAnnotation(ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    RequiresRole annotation = method.getAnnotation(RequiresRole.class);
    if (annotation == null) {
      annotation = joinPoint.getTarget().getClass().getAnnotation(RequiresRole.class);
    }
    return annotation;
  }

  private List<String> resolveCurrentUserRoles() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new AccessDeniedException("Authentication is required for role checking.");
    }

    return authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();
  }
}

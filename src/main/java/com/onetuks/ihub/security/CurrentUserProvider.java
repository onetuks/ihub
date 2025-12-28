package com.onetuks.ihub.security;

import com.onetuks.ihub.entity.user.User;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

  public User resolveUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (User) Objects.requireNonNull(authentication).getPrincipal();
  }
}

package com.onetuks.ihub.entity.project;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProjectMemberRole {
  PROJECT_VIEWER(0),
  PROJECT_MEMBER(1),
  PROJECT_OWNER(2);

  private final int weight;

  public boolean hasMuchAuthorityThan(ProjectMemberRole role) {
    return weight >= role.weight;
  }
}

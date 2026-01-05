package com.onetuks.ihub.dto.system;

import com.onetuks.ihub.entity.system.SystemEnvironment;
import com.onetuks.ihub.entity.system.SystemType;
import jakarta.validation.constraints.NotNull;

public record SystemCreateRequest(
    String systemCode,
    String description,
    @NotNull SystemType systemType,
    @NotNull SystemEnvironment environment
) {

}

package com.onetuks.ihub.dto.interfaces;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record InterfaceRevisionCreateRequest(
    @NotNull String interfaceId,
    @NotNull Integer versionNo,
    @NotNull String changedById,
    Map<String, String> snapshot,
    String reason
) {
}

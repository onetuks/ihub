package com.onetuks.ihub.dto.connection;

import com.onetuks.ihub.entity.connection.ConnectionStatus;
import com.onetuks.ihub.entity.connection.Protocol;

public record ConnectionUpdateRequest(
    String projectId,
    String systemId,
    String name,
    Protocol protocol,
    String host,
    Integer port,
    String path,
    String username,
    String authType,
    String extraConfig,
    ConnectionStatus status,
    String description,
    String updatedById
) {
}

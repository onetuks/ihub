package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.interfaces.ChannelAdapter;
import com.onetuks.ihub.entity.interfaces.InterfaceType;
import com.onetuks.ihub.entity.interfaces.SyncAsyncType;
import com.onetuks.ihub.entity.project.ProjectStatus;
import com.onetuks.ihub.entity.user.UserStatus;
import java.time.LocalDateTime;

public record InterfaceResponse(
    String interfaceId,
    String projectId,
    ProjectStatus projectStatus,
    String projectName,
    String ifId,
    String sourceSystemId,
    String targetSystemId,
    String module,
    InterfaceType interfaceType,
    String pattern,
    ChannelAdapter senderAdapter,
    ChannelAdapter receiverAdapter,
    SyncAsyncType sa,
    String statusId,
    String batchTimeLabel,
    String remark,
    String createdById,
    UserStatus createdByStatus,
    String createdByName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}

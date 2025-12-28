package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.interfaces.ChannelAdapter;
import com.onetuks.ihub.entity.interfaces.InterfaceType;
import com.onetuks.ihub.entity.interfaces.SyncAsyncType;
import jakarta.validation.constraints.NotNull;

public record InterfaceCreateRequest(
    @NotNull String projectId,
    String ifId,
    @NotNull String sourceSystemId,
    @NotNull String targetSystemId,
    String module,
    @NotNull InterfaceType interfaceType,
    String pattern,
    @NotNull ChannelAdapter senderAdapter,
    @NotNull ChannelAdapter receiverAdapter,
    @NotNull SyncAsyncType sa,
    @NotNull String statusId,
    String batchTimeLabel,
    String remark,
    @NotNull String createdById
) {

}

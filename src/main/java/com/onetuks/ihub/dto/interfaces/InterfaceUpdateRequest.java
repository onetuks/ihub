package com.onetuks.ihub.dto.interfaces;

import com.onetuks.ihub.entity.interfaces.ChannelAdapter;
import com.onetuks.ihub.entity.interfaces.InterfaceType;
import com.onetuks.ihub.entity.interfaces.SyncAsyncType;

public record InterfaceUpdateRequest(
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
    String remark
) {
}

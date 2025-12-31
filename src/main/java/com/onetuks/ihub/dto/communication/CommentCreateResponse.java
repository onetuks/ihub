package com.onetuks.ihub.dto.communication;

import java.util.List;

public record CommentCreateResponse(
    CommentResponse comment,
    List<MentionResponse> mentions
) {

}

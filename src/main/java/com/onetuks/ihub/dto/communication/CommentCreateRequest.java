package com.onetuks.ihub.dto.communication;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record CommentCreateRequest(
    @NotBlank String content,
    String parentCommentId,
    List<String> mentions
) {

}

package com.onetuks.ihub.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
    int status,
    String code,
    String message,
    List<String> errors,
    String path,
    LocalDateTime timestamp) {

  public static ApiError of(
      int status, String code, String message, List<String> errors, String path) {
    return new ApiError(status, code, message, errors, path, LocalDateTime.now());
  }
}

package com.developersboard.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TodoResourceUnavailableException extends RuntimeException {

  /**
   * Constructs a new runtime exception with {@code null} as its
   * detail message.  The cause is not initialized, and may subsequently be
   * initialized by a call to {@link #initCause}.
   */
  public TodoResourceUnavailableException() {
    super();
  }

  public TodoResourceUnavailableException(String message) {
    super(message);
  }
}

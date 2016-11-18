package com.dotsub.assignment.common.exceptions;

/**
 * This exception class should be used and thrown when the request cannot be identified properly
 * Used for Http Status 405 Method Not Allowed
 *
 * @author Muhammad Salman
 */

public class MethodNotAllowedException extends ApiException {

  public MethodNotAllowedException(String msg, String errorKey) {
    super(msg, errorKey);
  }

  public MethodNotAllowedException(String msg, String errorKey, Throwable cause) {
    super(msg, errorKey, cause);
  }
}

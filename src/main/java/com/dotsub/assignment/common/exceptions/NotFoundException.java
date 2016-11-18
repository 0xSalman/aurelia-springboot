package com.dotsub.assignment.common.exceptions;

/**
 * This exception should be used and thrown when the requested data is not found
 * Used for Http Status 204 Not Found
 *
 * @author Muhammad Salman
 */

public class NotFoundException extends ApiException {

  public NotFoundException(String msg, String errorKey) {
    super(msg, errorKey);
  }

  public NotFoundException(String msg, String errorKey, Throwable cause) {
    super(msg, errorKey, cause);
  }
}

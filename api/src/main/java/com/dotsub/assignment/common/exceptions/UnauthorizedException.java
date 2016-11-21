package com.dotsub.assignment.common.exceptions;

/**
 * This exception class should be used and thrown when user is not authorized to make a request
 * Used for HTTP Status 401 Unauthorized
 *
 * @author Muhammad Salman
 */

public class UnauthorizedException extends ApiException {

  public UnauthorizedException(String msg, String errorKey) {
    super(msg, errorKey);
  }

  public UnauthorizedException(String msg, String errorKey, Throwable cause) {
    super(msg, errorKey, cause);
  }
}

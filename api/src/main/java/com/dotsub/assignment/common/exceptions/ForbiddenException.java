package com.dotsub.assignment.common.exceptions;

/**
 * This exception class should be used and thrown when the server refuses to fulfill the request
 * even with proper authentication
 * Used for Http Status 403 Forbidden
 *
 * @author Muhammad Salman
 */

public class ForbiddenException extends ApiException {

  public ForbiddenException(String msg, String errorKey) {
    super(msg, errorKey);
  }

  public ForbiddenException(String msg, String errorKey, Throwable cause) {
    super(msg, errorKey, cause);
  }
}

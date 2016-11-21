package com.dotsub.assignment.common.exceptions;

/**
 * This exception should be used and thrown when a bad request is made
 * Used for Http Status 400 Bad Request
 *
 * @author Muhammad Salman
 */

public class BadRequestException extends ApiException {

  public BadRequestException(String msg, String errorKey) {
    super(msg, errorKey);
  }

  public BadRequestException(String msg, String errorKey, Throwable cause) {
    super(msg, errorKey, cause);
  }
}

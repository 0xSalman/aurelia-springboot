package com.dotsub.assignment.common.exceptions;

/**
 * This exception should be used and thrown when there is conflict in the resource state
 * Used for Http Status 409 Conflict
 *
 * @author Muhammad Salman
 */

public class ConflictException extends ApiException {

  public ConflictException(String msg, String errorKey) {
    super(msg, errorKey);
  }

  public ConflictException(String msg, String errorKey, Throwable cause) {
    super(msg, errorKey, cause);
  }
}

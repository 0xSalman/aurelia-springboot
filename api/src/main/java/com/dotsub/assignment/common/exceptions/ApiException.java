package com.dotsub.assignment.common.exceptions;

import java.util.HashMap;
import java.util.Map;
import lombok.ToString;

/**
 * Top level custom exception class
 *
 * @author Muhammad Salman
 */

@ToString
public class ApiException extends RuntimeException {

  public String message;
  public String errorKey;
  public Map<String, Object> errorFields = new HashMap<>();
  public Map<String, Object> context = new HashMap<>();

  public ApiException(String msg, String errorKey) {
    super(msg);
    this.message = msg;
    this.errorKey = errorKey;
  }

  public ApiException(String msg, String errorKey, Throwable cause) {
    super(msg, cause);
    this.message = msg;
    this.errorKey = errorKey;
  }

  public ApiException addErrorField(String fieldName, String message) {
    errorFields.put(fieldName, message);
    return this;
  }

  public ApiException addErrorField(Map<String, Object> contextMap) {
    this.errorFields.putAll(errorFields);
    return this;
  }

  public ApiException addContext(String fieldName, Object fieldValue) {
    context.put(fieldName, fieldValue);
    return this;
  }

  @SuppressWarnings("unchecked")
  public ApiException addContext(Map<String, Object> contextMap) {
    this.context.putAll(contextMap);
    return this;
  }
}

package com.dotsub.assignment.common.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
  public List<String> errorFields = new ArrayList<>();
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

  public ApiException addErrorField(String fieldName) {
    errorFields.add(fieldName);
    return this;
  }

  public ApiException addErrorField(List<String> errorFields) {
    this.errorFields = new ArrayList<>(errorFields);
    return this;
  }

  public ApiException addContext(String fieldName, Object fieldValue) {
    context.put(fieldName, fieldValue);
    return this;
  }

  @SuppressWarnings("unchecked")
  public ApiException addContext(Object... context) {
    if (context != null && context.length == 1) {
      Map<String, Object> contextMap = (Map<String, Object>) context[0];
      this.context.putAll(contextMap);
    }
    return this;
  }
}

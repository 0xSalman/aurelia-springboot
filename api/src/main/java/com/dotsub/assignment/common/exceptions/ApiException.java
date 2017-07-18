package com.dotsub.assignment.common.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.boot.logging.LogLevel;

/**
 * Top level custom exception class
 *
 * @author Muhammad Salman
 */

@ToString
public class ApiException extends RuntimeException {

  public String message;
  public String errorKey;
  public List<ErrorField> errorFields = new ArrayList<>();
  public Map<String, Object> context = new HashMap<>();
  public LogLevel logLevel;

  public ApiException(String msg, String errorKey) {
    super(msg);
    this.message = msg;
    this.errorKey = errorKey;
    logLevel = LogLevel.ERROR;
  }

  public ApiException(String msg, String errorKey, Throwable cause) {
    super(msg, cause);
    this.message = msg;
    this.errorKey = errorKey;
    logLevel = LogLevel.ERROR;
  }

  public ApiException logLevel(LogLevel logLevel) {
    this.logLevel = logLevel;
    return this;
  }

  public ApiException addErrorField(ErrorField errorField) {
    if (errorField != null) {
      this.errorFields.add(errorField);
    }
    return this;
  }

  public ApiException addErrorField(List<ErrorField> errorFields) {
    if (errorFields != null) {
      this.errorFields.addAll(errorFields);
    }
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

  @AllArgsConstructor
  @ToString
  public static class ErrorField {
    public String name;
    public String message;
  }
}

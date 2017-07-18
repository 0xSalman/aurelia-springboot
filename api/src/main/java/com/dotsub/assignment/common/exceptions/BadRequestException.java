package com.dotsub.assignment.common.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;

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

  public BadRequestException(BindingResult bindingResult) {
    this("Invalid input", ErrorKey.INVALID_INPUT);
    bindingResult.getFieldErrors().forEach(error -> {
      addContext(error.getField(), error.getRejectedValue());
      addErrorField(new ErrorField(error.getField(), error.getDefaultMessage()));
    });
  }

  public BadRequestException(JsonMappingException cause) {
    this("Invalid input", ErrorKey.INVALID_INPUT, cause);
    cause.getPath().forEach(reference -> {
//      this.addContext(reference.getFieldName(), "");
      addErrorField(new ErrorField(reference.getFieldName(), "invalid"));
    });
  }

  public BadRequestException(MissingServletRequestParameterException cause) {
    this("Invalid input", ErrorKey.INVALID_INPUT, cause);
    addContext(cause.getParameterName(), cause.getParameterType());
    addErrorField(new ErrorField(cause.getParameterName(), "required"));
  }
}

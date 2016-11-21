package com.dotsub.assignment.common.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dotsub.assignment.common.LoggerUtil;

/**
 * Top level Exception controller which will handle all the exceptions
 * thrown from other controllers. This controller can be used
 * to customize the error response sent back for specific exception.
 *
 * @author Muhammad Salman
 */

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ExceptionHandlerController {

  @ExceptionHandler({HttpMessageNotReadableException.class, BindException.class,
                      MissingServletRequestParameterException.class,
                      BadRequestException.class})
  public ResponseEntity<Map> handleBadRequest(final Exception ex) {

    ApiException badRequestEx;

    if (ex instanceof BadRequestException) {
      badRequestEx = (BadRequestException) ex;
    } else {
      if (ex instanceof BindException) {
        badRequestEx = new BadRequestException("Required data is invalid", ErrorKey.INVALID_INPUT, ex);
        BindException bindException = (BindException) ex;
        for (FieldError fieldError : bindException.getFieldErrors()) {
          badRequestEx.addErrorField(fieldError.getField(), fieldError.getDefaultMessage())
                      .addContext(fieldError.getField(), fieldError.getRejectedValue());
        }
      } else {
        badRequestEx = new BadRequestException("Bad request", ErrorKey.BAD_REQUEST, ex);
      }
    }

    return errorResponse(badRequestEx, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Map> handleUnauthorized(final UnauthorizedException ex) {
    return errorResponse(ex, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<Map> handleForbidden(final ForbiddenException ex) {
    return errorResponse(ex, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Map> handleNotFound(final NotFoundException ex) {
    return errorResponse(ex, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<Map> handleConflict(final ConflictException ex) {
    return errorResponse(ex, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(value = {ApiException.class, Exception.class})
  public ResponseEntity<Map> handleOthers(final Exception ex) {
    ApiException exception;
    exception = (ex instanceof ApiException) ? (ApiException) ex :
                  new ApiException("Internal server error", ErrorKey.SERVER_ERROR, ex);
    return errorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<Map> errorResponse(ApiException apiException, HttpStatus errorCode) {
    LoggerUtil.logError(apiException);
    Map<String, Object> response = new HashMap<>();
    response.put("message", apiException.message);
    response.put("code", errorCode.value());
    response.put("errorKey", apiException.errorKey);
    response.put("errorFields", apiException.errorFields);
    response.put("context", apiException.context);
    return new ResponseEntity<>(response, errorCode);
  }
}
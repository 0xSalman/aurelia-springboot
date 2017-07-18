package com.dotsub.assignment.common.exceptions;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
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

  @ExceptionHandler(
    { BadRequestException.class,
      BindException.class, IllegalArgumentException.class,
      HttpMessageNotReadableException.class,
      MissingServletRequestParameterException.class,
    })
  public ResponseEntity<Map> handleBadRequest(final Exception ex) {

    ApiException badRequestEx = null;

    if (ex instanceof BadRequestException) {
      badRequestEx = (BadRequestException) ex;
    } else {
      if (ex instanceof BindException) {
        badRequestEx = new BadRequestException((BindException) ex);
      } else if (ex instanceof IllegalArgumentException || ex instanceof HttpMessageNotReadableException) {
        Throwable cause = ex.getCause();
        if (cause instanceof JsonMappingException) {
          badRequestEx = new BadRequestException((JsonMappingException) cause);
        }
      } else if (ex instanceof MissingServletRequestParameterException) {
        badRequestEx = new BadRequestException((MissingServletRequestParameterException) ex);
      }
    }

    if (badRequestEx == null) {
      badRequestEx = new BadRequestException("Bad request", ErrorKey.BAD_REQUEST, ex);
    }

    return errorResponse(badRequestEx, HttpStatus.BAD_REQUEST);
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
    ApiException exception =
      (ex instanceof ApiException) ? (ApiException) ex :
        new ApiException("Internal server error", ErrorKey.SERVER_ERROR, ex);
    return errorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<Map> errorResponse(ApiException apiException, HttpStatus errorCode) {
    LoggerUtil.logError(apiException);
    Map<String, Object> response = new HashMap<>();
    response.put("message", apiException.message);
    response.put("status", errorCode.value());
    response.put("errorKey", apiException.errorKey);
    response.put("errorFields", apiException.errorFields);
    response.put("context", apiException.context);
    return new ResponseEntity<>(response, errorCode);
  }
}

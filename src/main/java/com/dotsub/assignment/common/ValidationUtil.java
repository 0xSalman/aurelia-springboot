package com.dotsub.assignment.common;

import com.dotsub.assignment.common.exceptions.BadRequestException;
import com.dotsub.assignment.common.exceptions.ErrorKey;
import com.dotsub.assignment.common.exceptions.NotFoundException;

/**
 * Util class to handle common validation functionality
 *
 * @author Muhammad Salman
 */

public class ValidationUtil {

  public static void notNull(Object object, String errorMessage) {
    if (object == null) {
      throw new BadRequestException(errorMessage, ErrorKey.INVALID_INPUT);
    }
  }

  public static void notFound(String property, Object value, String errorKey, Object criteria) {
    if (value == null) {
      String errorMessage = StringUtil.convertCamelCase(property) + " not found";
      throw new NotFoundException(errorMessage, errorKey).addContext("criteria", criteria);
    }
  }

  public static void notBlank(String str, String errorMessage) {
    if (StringUtil.isBlank(str)) {
      throw new BadRequestException(errorMessage, ErrorKey.INVALID_INPUT);
    }
  }

  public static void notBlank(String property, String value, String errorKey) {
    if (StringUtil.isBlank(value)) {
      String errorMessage = StringUtil.convertCamelCase(property) + " can not be empty";
      throw new BadRequestException(errorMessage, errorKey)
              .addErrorField(property)
              .addContext(property, value);
    }
  }
}

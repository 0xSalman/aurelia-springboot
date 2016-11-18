package com.dotsub.assignment.common;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * Date related common functionality
 *
 * @author Muhammad Salman
 */

public class DateUtil {

  public static OffsetDateTime now() {
    return OffsetDateTime.now(ZoneOffset.UTC);
  }
}

package com.dotsub.assignment.common;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Date related common functionality
 *
 * @author Muhammad Salman
 */

public class DateUtil {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

  public static OffsetDateTime now() {
    return OffsetDateTime.now(ZoneOffset.UTC);
  }

  public static String formattedNow() {
    return now().format(formatter);
  }
}

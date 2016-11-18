package com.dotsub.assignment.common.db;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;

/**
 * Custom converter for mongo that converts Date to java 8 OffsetDateTime
 *
 * @author Muhammad Salman
 */

public class DateToOffsetDateTimeConverter implements Converter<Date, OffsetDateTime> {

  @Override
  public OffsetDateTime convert(Date source) {
    return source.toInstant().atOffset(ZoneOffset.UTC);
  }

  @Override
  public String toString() {
    return "{" + Date.class.getSimpleName() + " -> " + OffsetDateTime.class.getSimpleName() + "}";
  }
}

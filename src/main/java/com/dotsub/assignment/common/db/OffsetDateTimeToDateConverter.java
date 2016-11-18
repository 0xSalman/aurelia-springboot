package com.dotsub.assignment.common.db;

import java.time.OffsetDateTime;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;

/**
 * Custom converter for mongo that converts java 8 OffsetDateTime to Date
 *
 * @author Muhammad Salman
 */

public class OffsetDateTimeToDateConverter implements Converter<OffsetDateTime, Date> {

  @Override
  public Date convert(OffsetDateTime source) {
    return Date.from(source.toInstant());
  }

  @Override
  public String toString() {
    return "{" + OffsetDateTime.class.getSimpleName() + " -> " + Date.class.getSimpleName() + "}";
  }
}

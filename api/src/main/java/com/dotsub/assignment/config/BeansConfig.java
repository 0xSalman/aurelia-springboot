package com.dotsub.assignment.config;

import java.util.Arrays;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import com.dotsub.assignment.common.db.DateToOffsetDateTimeConverter;
import com.dotsub.assignment.common.db.OffsetDateTimeToDateConverter;

/**
 * Manually configured beans for spring container
 *
 * @author Muhammad Salman
 */

@Configuration
public class BeansConfig {

  @Bean
  public JavaTimeModule javaTimeModule() {
    return new JavaTimeModule();
  }

  @Bean
  public CustomConversions customConversions() {
    return new CustomConversions(
                                  Arrays.asList(new DateToOffsetDateTimeConverter(), new OffsetDateTimeToDateConverter())
    );
  }
}

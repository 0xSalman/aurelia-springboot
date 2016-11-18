package com.dotsub.assignment;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Muhammad Salman
 */

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class).run(args);
  }
}

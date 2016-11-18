package com.dotsub.assignment.common;

/**
 * Custom utility class to manipulate string extending existing available class
 * in Spring library
 *
 * @author Muhammad Salman
 */

public class StringUtil extends org.springframework.util.StringUtils {

  public static boolean isBlank(String str) {
    return str == null || str.trim().isEmpty();
  }

  public static boolean isNotBlank(String str) {
    return !isBlank(str);
  }

  /**
   * Converts a camel case string to spaced string
   */
  public static String convertCamelCase(String camelCase) {
    String nonCamelCase = camelCase.replaceAll("(.)(\\p{Upper})", "$1 $2");
    nonCamelCase = nonCamelCase.substring(0, 1).toUpperCase() + nonCamelCase.substring(1);
    return nonCamelCase;
  }
}
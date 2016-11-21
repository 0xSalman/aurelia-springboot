package com.dotsub.assignment.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;

/**
 * A logger utility class
 *
 * @author Muhammad Salman
 */

public class LoggerUtil {

  private static Object[] getCallerInfo() {
    Object[] info = new Object[2];
    try {
      StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
      String callerClassName = stackTraceElements[3].getClassName();
      String methodName = stackTraceElements[3].getMethodName();
      info[0] = Class.forName(callerClassName);
      info[1] = methodName;
      return info;
    } catch (ClassNotFoundException e) {
      Logger logger = LoggerFactory.getLogger(LoggerUtil.class);
      logger.error("Exception ", e);
      info[0] = LoggerUtil.class;
      info[1] = "";
    }
    return info;
  }

  public static void logError(String message) {
    Logger logger = LoggerFactory.getLogger((Class<?>) getCallerInfo()[0]);
    logger.error(message);
  }

  public static void logError(Throwable ex) {
    Logger logger = LoggerFactory.getLogger((Class<?>) getCallerInfo()[0]);
    logger.error("{}", new Object[]{ex}, ex);
  }

  public static void logError(String message, Object... params) {
    Logger logger = LoggerFactory.getLogger((Class<?>) getCallerInfo()[0]);
    logger.error(message, params);
  }

  public static void logError(String message, Throwable ex) {
    Logger logger = LoggerFactory.getLogger((Class<?>) getCallerInfo()[0]);
    logger.error(message, ex);
  }

  public static void logInfo(String message) {
    Logger logger = LoggerFactory.getLogger((Class<?>) getCallerInfo()[0]);
    logger.info(message);
  }

  public static void logInfo(String message, Object... params) {
    Logger logger = LoggerFactory.getLogger((Class<?>) getCallerInfo()[0]);
    logger.info(message, params);
  }

  public static void logInfo(String message, Throwable ex) {
    Logger logger = LoggerFactory.getLogger((Class<?>) getCallerInfo()[0]);
    logger.info(message, ex);
  }

  public static void logDebug(String message) {
    Logger logger = LoggerFactory.getLogger((Class<?>) getCallerInfo()[0]);
    logger.debug(message);
  }

  public static void logDebug(String message, Object... params) {
    Logger logger = LoggerFactory.getLogger((Class<?>) getCallerInfo()[0]);
    logger.debug(message, params);
  }

  public static void logDebug(String message, Throwable ex) {
    Logger logger = LoggerFactory.getLogger((Class<?>) getCallerInfo()[0]);
    logger.debug(message, ex);
  }

  public static long logEnter(LogLevel logType) {
    Object[] obj = getCallerInfo();
    String methodName = obj[1].toString();
    Logger logger = LoggerFactory.getLogger((Class<?>) obj[0]);
    if (logType.equals(LogLevel.INFO)) {
      logger.info("ENTER - {}()", methodName);
    } else {
      logger.debug("ENTER - {}()", methodName);
    }
    return System.currentTimeMillis();
  }


  public static long logEnter(LogLevel logType, Object... params) {
    Object[] obj = getCallerInfo();
    String methodName = obj[1].toString();
    Logger logger = LoggerFactory.getLogger((Class<?>) obj[0]);
    if (logType.equals(LogLevel.INFO)) {
      logger.info("ENTER - {}({})", methodName, params);
    } else {
      logger.debug("ENTER - {}({})", methodName, params);
    }
    return System.currentTimeMillis();
  }

  public static void logExit(LogLevel logType, long startTime) {
    Object[] obj = getCallerInfo();
    String methodName = obj[1].toString();
    Logger logger = LoggerFactory.getLogger((Class<?>) obj[0]);
    long runTime = System.currentTimeMillis() - startTime;
    if (logType.equals(LogLevel.INFO)) {
      logger.info("EXIT - {}() in {}ms", methodName, runTime);
    } else {
      logger.debug("EXIT - {}() in {}ms", methodName, runTime);
    }
  }

  public static void logExit(LogLevel logType) {
    Object[] obj = getCallerInfo();
    String methodName = obj[1].toString();
    Logger logger = LoggerFactory.getLogger((Class<?>) obj[0]);
    if (logType.equals(LogLevel.INFO)) {
      logger.info("EXIT - {}()", methodName);
    } else {
      logger.debug("EXIT - {}()", methodName);
    }
  }

  public static void logExit(LogLevel logType, Object returnValue, long startTime) {
    Object[] obj = getCallerInfo();
    String methodName = obj[1].toString();
    Logger logger = LoggerFactory.getLogger((Class<?>) obj[0]);
    long runTime = System.currentTimeMillis() - startTime;
    if (logType.equals(LogLevel.INFO)) {
      logger.info("EXIT - {}() in {}ms > {}", methodName, runTime, returnValue);
    } else {
      logger.debug("EXIT - {}() in {}ms > ({})", methodName, runTime, returnValue);
    }
  }

  public static void logExit(LogLevel logType, Object returnValue) {
    Object[] obj = getCallerInfo();
    String methodName = obj[1].toString();
    Logger logger = LoggerFactory.getLogger((Class<?>) obj[0]);
    if (logType.equals(LogLevel.INFO)) {
      logger.info("EXIT - {}() > {}", methodName, returnValue);
    } else {
      logger.debug("EXIT - {}() > ({})", methodName, returnValue);
    }
  }
}

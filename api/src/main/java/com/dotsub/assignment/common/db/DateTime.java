package com.dotsub.assignment.common.db;

import java.time.OffsetDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.dotsub.assignment.common.DateUtil;

/**
 * Common date time fields to add in database collections
 *
 * @author Muhammad Salman
 */

public abstract class DateTime {

  @CreatedDate
  public OffsetDateTime createTS = DateUtil.now();
  @LastModifiedDate
  public OffsetDateTime updateTS = DateUtil.now();
}

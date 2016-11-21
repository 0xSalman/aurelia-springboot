package com.dotsub.assignment;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dotsub.assignment.common.db.DateTime;

/**
 * @author Muhammad Salman
 */

@Document
@ToString
public class UserFile extends DateTime {

  @Id
  public String id;
  public String fileName;
  @Setter
  @NotNull
  @Size(min = 1, message = "cannot be empty")
  public String title;
  @Setter
  @NotNull
  @Size(min = 10, message = "must have at least 10 characters")
  public String description;
  public OffsetDateTime creationTS;

  // convert to utc
  public void setCreationTS(String creationTS) {
    this.creationTS = OffsetDateTime.parse(creationTS).withOffsetSameInstant(ZoneOffset.UTC);
  }
}

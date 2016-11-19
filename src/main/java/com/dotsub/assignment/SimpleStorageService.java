package com.dotsub.assignment;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dotsub.assignment.common.LoggerUtil;

/**
 * A simpler implementation of storage service to save incoming user files on
 * local file system and their metadata in mongodb
 *
 * It also implements find methods
 *
 * @author Muhammad Salman
 */

@Service
public class SimpleStorageService implements StorageService {

  @Autowired
  private MongoOperations mongo;
  @Value("${dsas.upload.dir}")
  private String uploadDir;

  /**
   * Save provided file to local system and metadata to mongodb
   *
   * @param userFile - file meta data
   * @param file - actual file
   */
  @Override
  public void add(UserFile userFile, MultipartFile file) {

    LoggerUtil.logEnter(LogLevel.INFO, userFile, file.getName());

    LoggerUtil.logExit(LogLevel.INFO);
  }

  /**
   * Find metadata of all files
   *
   * @param pageable - pageable object for pagination
   * @return Page - object with list of all files and pagination info
   */
  @Override
  public Page<UserFile> findAll(Pageable pageable) {

    LoggerUtil.logEnter(LogLevel.INFO, pageable);

    Page<UserFile> page = new PageImpl(new ArrayList<>());

    LoggerUtil.logExit(LogLevel.INFO);
    return page;
  }

  /**
   * Find file from local system for provided fileId
   *
   * @param fileId - unique file id
   * @return Resource - file
   */
  @Override
  public Resource findOne(String fileId) {

    LoggerUtil.logEnter(LogLevel.INFO, fileId);

    LoggerUtil.logExit(LogLevel.INFO);
    return null;
  }
}

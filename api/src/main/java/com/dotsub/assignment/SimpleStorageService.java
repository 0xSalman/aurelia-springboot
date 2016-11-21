package com.dotsub.assignment;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dotsub.assignment.common.LoggerUtil;
import com.dotsub.assignment.common.ValidationUtil;
import com.dotsub.assignment.common.exceptions.ApiException;
import com.dotsub.assignment.common.exceptions.BadRequestException;
import com.dotsub.assignment.common.exceptions.ErrorKey;
import com.dotsub.assignment.common.exceptions.NotFoundException;

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
  private Path rootLocation;

  @Autowired
  public SimpleStorageService(@Value("${dsas.upload.dir}") String uploadDir, MongoOperations mongo) {
    this.mongo = mongo;
    this.rootLocation = Paths.get(uploadDir);
  }

  /**
   * Save provided file to local system and metadata to mongodb
   *
   * @param userFile - file meta data
   * @param file - actual file
   */
  @Override
  public void add(UserFile userFile, MultipartFile file) {

    LoggerUtil.logEnter(LogLevel.INFO, userFile, file.getName());

    if (file.isEmpty()) {
      throw new BadRequestException("File is empty", ErrorKey.FILE_CANNOT_SAVE)
              .addContext("fileMetadata", userFile).addContext("fileSize", file.getSize());
    }

    try {
      // save file metadata
      userFile.id = new ObjectId().toString();
      int i = file.getOriginalFilename().lastIndexOf('.');
      String extension = file.getOriginalFilename().substring(i + 1);
      userFile.fileName = String.join(".", userFile.id, extension);
      mongo.save(userFile);

      // save file to filesystem
      Files.copy(file.getInputStream(), rootLocation.resolve(userFile.fileName));
    } catch (Exception e) {
      throw new ApiException("Failed to save file", ErrorKey.FILE_CANNOT_SAVE, e).addContext("fileMetadata", userFile);
    }

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

    try {
      long total = mongo.count(new Query(), UserFile.class);
      List<UserFile> files = mongo.find(new Query().with(pageable), UserFile.class);
      Page<UserFile> page = new PageImpl<>(files, pageable, total);

      LoggerUtil.logExit(LogLevel.INFO);
      return page;
    } catch (Exception e) {
      throw new ApiException("Failed to find files", ErrorKey.FILE_CANNOT_FIND, e);
    }
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

    Map<String, Object> errorContext = new HashMap<>();
    errorContext.put("fileId", fileId);

    try {
      // query db
      UserFile userFile = mongo.findOne(new Query(Criteria.where("_id").is(fileId)), UserFile.class);
      ValidationUtil.notFound("file", userFile, ErrorKey.FILE_CANNOT_FIND, errorContext);

      // fetch file from file system
      Path file = rootLocation.resolve(userFile.fileName);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        LoggerUtil.logExit(LogLevel.INFO);
        return resource;
      } else {
        throw new NotFoundException("Could not read file", ErrorKey.FILE_CANNOT_FIND).addContext(errorContext);
      }
    } catch (Exception e) {
      if (e instanceof ApiException) {
        throw (ApiException) e;
      } else {
        throw new ApiException("Failed to find file", ErrorKey.FILE_CANNOT_FIND, e).addContext(errorContext);
      }
    }
  }
}

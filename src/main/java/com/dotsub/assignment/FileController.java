package com.dotsub.assignment;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dotsub.assignment.common.LoggerUtil;

/**
 * Handles incoming api requests to upload a file and find uploaded files
 *
 * @author Muhammad Salman
 */

@RestController
@RequestMapping("/api")
public class FileController {

  @Autowired
  private StorageService storageService;

  @PostMapping("/file")
  public ResponseEntity addFile(@Valid UserFile userFile, @RequestParam("file") MultipartFile file) {

    LoggerUtil.logEnter(LogLevel.INFO, userFile, file.getName());

    storageService.add(userFile, file);

    ResponseEntity response = new ResponseEntity(HttpStatus.OK);
    LoggerUtil.logExit(LogLevel.INFO);
    return response;
  }

  @GetMapping("/files")
  public Page<UserFile> getFiles(Pageable pageable) {

    LoggerUtil.logEnter(LogLevel.INFO, pageable);

    Page<UserFile> files = storageService.findAll(pageable);

    LoggerUtil.logExit(LogLevel.INFO);
    return files;
  }

  @GetMapping("/file/{id}")
  public ResponseEntity<Resource> serveFile(@PathVariable String id) {

    LoggerUtil.logEnter(LogLevel.INFO, id);

    Resource file = storageService.findOne(id);

    ResponseEntity<Resource> response =
      ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);

    LoggerUtil.logExit(LogLevel.INFO);
    return response;
  }
}

package com.dotsub.assignment;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for interacting with user files
 *
 * @author Muhammad Salman
 */

public interface StorageService {

  void add(UserFile userFile, MultipartFile file);

  Page<UserFile> findAll(Pageable pageable);

  Resource findOne(String userFileId);
}

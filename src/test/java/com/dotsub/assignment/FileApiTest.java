package com.dotsub.assignment;

import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.annotations.Test;

import com.dotsub.assignment.common.DateUtil;

/**
 * @author Muhammad Salman
 */

public class FileApiTest extends BaseTest {

  @Test
  public void add_file() {

    LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", new ClassPathResource("application.yml"));
    body.add("title", "application");
    body.add("description", "application config file");
    body.add("creationTS", DateUtil.formattedNow());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    HttpEntity requestEntity = new HttpEntity<>(body, headers);

    ResponseEntity response = restTemplate.postForEntity(baseUrl + "/file", requestEntity, ResponseEntity.class);
    assert response.getStatusCode().equals(HttpStatus.OK);
  }

  @Test

  public void find_all_files() {
    getFiles(new PageRequest(0, 25));
  }

  @SuppressWarnings("unchecked")
  private List<UserFile> getFiles(Pageable pageable) {

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/files")
                                                       .queryParam("page", pageable.getPageNumber())
                                                       .queryParam("size", pageable.getPageSize());

    ResponseEntity<Page> response = restTemplate.getForEntity(builder.build().encode().toUri(), Page.class);
    assert response.getStatusCode().equals(HttpStatus.OK);

    Page<UserFile> page = response.getBody();
    List<UserFile> files = page.getContent();
    assert files.size() > 0;

    return files;
  }

  @Test
  public void find_file() {

    List<UserFile> files = getFiles(new PageRequest(0, 1));
    UserFile firstFile = files.get(0);

    ResponseEntity<Resource> response = restTemplate.getForEntity(baseUrl + "/file/" + firstFile.id, Resource.class);
    assert response.getStatusCode().equals(HttpStatus.OK);
  }
}

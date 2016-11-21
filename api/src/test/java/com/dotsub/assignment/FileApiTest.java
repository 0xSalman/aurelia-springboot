package com.dotsub.assignment;

import java.util.List;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

  @Test(priority = 1)
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

  @Test(priority = 2)
  public void find_all_files() {
    getFiles(new PageRequest(0, 25));
  }

  @SuppressWarnings("unchecked")
  private List<UserFile> getFiles(Pageable pageable) {

    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/files")
                                                       .queryParam("page", pageable.getPageNumber())
                                                       .queryParam("size", pageable.getPageSize());

    ResponseEntity<Map> response = restTemplate.getForEntity(builder.build().encode().toUri(), Map.class);
    assert response.getStatusCode().equals(HttpStatus.OK);

    Map<String, Object> responseBody = response.getBody();
    List<UserFile> files = (List<UserFile>) responseBody.get("content");
    assert files.size() > 0;

    return files;
  }

  @Test(priority = 3)
  public void find_file() {

    List<UserFile> files = getFiles(new PageRequest(0, 1));
    UserFile firstFile = objectMapper.convertValue(files.get(0), UserFile.class);

    ResponseEntity<Resource> response = restTemplate.getForEntity(baseUrl + "/file/" + firstFile.id, Resource.class);
    assert response.getStatusCode().equals(HttpStatus.OK);
  }
}

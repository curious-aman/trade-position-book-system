package com.jpmc.pbs.definitions;

import com.jpmc.pbs.bean.Position;
import com.jpmc.pbs.bean.TradePositions;
import com.jpmc.pbs.exceptions.TPBSApiError;
import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.javacrumbs.jsonunit.core.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TPBSTestDefinitions extends TPBSIntegrationTest {
  ResponseEntity<TradePositions> responseEntity;
  ResponseEntity<Position> responsePositionEntity;
  ResponseEntity<Object> responseEntityObject;
  TPBSApiError tPBSApiError;
  String url = DEFAULT_URL;

  @Autowired private Map<String, Position> positionsDataMap;

  @When("^I set http body with file (.*)$")
  public void sendPostRequest(String filePath) throws IOException {
    headers.setContentType(MediaType.APPLICATION_JSON);
    String body =
        StreamUtils.copyToString(
            getClass().getClassLoader().getResourceAsStream(filePath), StandardCharsets.UTF_8);
    HttpEntity<String> request = new HttpEntity<>(body, headers);
    responseEntity =
        restTemplate.postForEntity(url + "/api/v1/events", request, TradePositions.class);
  }

  @When("^Get Position details using (.*)$")
  public void sendGetRequest(String resource) {
    try{
    responsePositionEntity =
        restTemplate.getForEntity(
            url + "/api/v1/position" + resource, Position.class);
    }catch (HttpClientErrorException e){
      int statusCode = e.getRawStatusCode();
      responsePositionEntity = ResponseEntity.status(HttpStatus.valueOf(statusCode)).build();
    }
  }

  @When("^I set http body with invalid json (.*)$")
  public void sendInvalidRequest(String jsonMessage) {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> request = new HttpEntity<>(jsonMessage, headers);
    try {
      responseEntityObject =
          restTemplate.postForEntity(url + "/api/v1/events", request, Object.class);
    } catch (HttpClientErrorException e) {
      int statusCode = e.getRawStatusCode();
      responseEntityObject = ResponseEntity.status(HttpStatus.valueOf(statusCode)).build();
    }
  }

  @Then("^http response code should be (\\d+)$")
  public void responseCode(String status) {
  }

  @Then("^Position http response code should be (\\d+)$")
  public void positionResponseCode(String status) {
    this.checkPositionStatus(status, false);
  }

  @Then("^Invalid http response code should be (\\d+)$")
  public void invalidResponseCode(String status) {
    this.checkInvalidStatus(status, false);
  }

  @Then("^http response header (.*) should be (.*)$")
  public void headerEqual(String headerName, String headerValue) {
    this.checkHeaderEqual(headerName, headerValue, false);
  }

  @Then("^Response is same to output in file (.*)$")
  public void responseValue(String filePath) throws IOException {
    String body =
        StreamUtils.copyToString(
            getClass().getClassLoader().getResourceAsStream(filePath), StandardCharsets.UTF_8);
    assertThatJson(body).when(Option.IGNORING_ARRAY_ORDER).isEqualTo(responseEntity.getBody());
  }

  @Then("^Position Response is same to output in file (.*)$")
  public void positionResponseValue(String filePath) throws IOException {
    String body =
            StreamUtils.copyToString(
                    getClass().getClassLoader().getResourceAsStream(filePath), StandardCharsets.UTF_8);
    assertThatJson(body).when(Option.IGNORING_ARRAY_ORDER).isEqualTo(responsePositionEntity.getBody());
  }

  @After
  public void afterScenario() {
    positionsDataMap.clear();
  }

  void checkStatus(String status, boolean isNot) {
    int sanitizedStatus = Integer.parseInt(status);
    assertThat(sanitizedStatus).isPositive();

    if (isNot) {
      assertThat(responseEntity.getStatusCodeValue()).isNotEqualTo(sanitizedStatus);
    } else {
      assertThat(responseEntity.getStatusCodeValue()).isEqualTo(sanitizedStatus);
    }
  }

  void checkPositionStatus(String status, boolean isNot) {
    int sanitizedStatus = Integer.parseInt(status);
    assertThat(sanitizedStatus).isPositive();

    if (isNot) {
      assertThat(responsePositionEntity.getStatusCodeValue()).isNotEqualTo(sanitizedStatus);
    } else {
      assertThat(responsePositionEntity.getStatusCodeValue()).isEqualTo(sanitizedStatus);
    }
  }

  void checkInvalidStatus(String status, boolean isNot) {
    int sanitizedStatus = Integer.parseInt(status);
    assertThat(sanitizedStatus).isPositive();

    if (isNot) {
      assertThat(responseEntityObject.getStatusCodeValue()).isNotEqualTo(sanitizedStatus);
    } else {
      assertThat(responseEntityObject.getStatusCodeValue()).isEqualTo(sanitizedStatus);
    }
  }

  void checkHeaderEqual(String headerName, String headerValue, boolean isNot) {
    assertThat(headerName).isNotEmpty();
    assertThat(headerValue).isNotEmpty();
    assertThat(responseEntity.getHeaders()).isNotNull();

    if (!isNot) {
      assertThat(responseEntity.getHeaders().getFirst(headerName)).contains(headerValue);
    } else {
      assertThat(responseEntity.getHeaders().getFirst(headerName)).doesNotContain(headerValue);
    }
  }
}

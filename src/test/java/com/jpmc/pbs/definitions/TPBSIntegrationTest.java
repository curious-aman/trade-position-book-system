package com.jpmc.pbs.definitions;

import com.jpmc.pbs.TradePositionBookApplication;
import com.jpmc.pbs.bean.Position;
import io.cucumber.spring.CucumberContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest(
    classes = TradePositionBookApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@CucumberContextConfiguration
public abstract class TPBSIntegrationTest {
  private static final Logger LOG = LoggerFactory.getLogger(TPBSIntegrationTest.class);
  protected RestTemplate restTemplate = new RestTemplate();

  protected HttpHeaders headers = new HttpHeaders();

  protected final String DEFAULT_URL = "http://localhost:8082/";

}

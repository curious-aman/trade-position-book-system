package com.jpmc.pbs.config;

import com.jpmc.pbs.bean.Position;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class TPBSConfiguration {
  @Bean
  public Map<String, Position> positionsDataMap() {
    return new ConcurrentHashMap<>();
  }
}

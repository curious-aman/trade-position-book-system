package com.jpmc.pbs.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
  @JsonProperty("Account")
  private String account;

  @JsonProperty("Security")
  private String security;

  @JsonProperty("Quantity")
  private Long quantity;

  @JsonProperty("Events")
  private List<Event> events;

  @JsonIgnore private Map<Long, Long> quantityMap;
}

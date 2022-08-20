package com.jpmc.pbs.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeEvents {
  @JsonProperty("Events")
  private List<Event> events;
}

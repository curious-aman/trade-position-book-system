package com.jpmc.pbs.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradePositions {
  @JsonProperty("Positions")
  private List<Position> positions;
}

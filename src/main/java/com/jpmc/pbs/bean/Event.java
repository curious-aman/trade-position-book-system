package com.jpmc.pbs.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmc.pbs.enums.Action;
import lombok.*;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
  @JsonProperty("ID")
  private Long id;

  @JsonProperty("Action")
  private Action action;

  @JsonProperty("Account")
  private String account;

  @JsonProperty("Security")
  private String security;

  @JsonProperty("Quantity")
  private Long quantity;
}

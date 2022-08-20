package com.jpmc.pbs.controller;

import com.jpmc.pbs.bean.Position;
import com.jpmc.pbs.bean.TradeEvents;
import com.jpmc.pbs.bean.TradePositions;
import com.jpmc.pbs.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/")
public class EventController {
  @Autowired EventService eventService;

  @PostMapping(path = "v1/events", consumes = "application/json", produces = "application/json")
  public ResponseEntity<TradePositions> processEvent(@RequestBody TradeEvents events) {
    eventService.savePosition(events.getEvents());
    TradePositions tradePositions =
        TradePositions.builder().positions(eventService.getPositions()).build();
    return ResponseEntity.ok(tradePositions);
  }

  @GetMapping(path = "v1/positions", produces = "application/json")
  public ResponseEntity<TradePositions> getPositions() {
    TradePositions tradePositions =
        TradePositions.builder().positions(eventService.getPositions()).build();
    return ResponseEntity.ok(tradePositions);
  }

  @GetMapping(path = "v1/position/{account}/{security}", produces = "application/json")
  public ResponseEntity<Position> getPositionBySecurityAndAccount(
      @PathVariable String account, @PathVariable String security) {
    Position position = eventService.getPositionBySecurityAndAccount(security, account);
    if (position != null) {
      return ResponseEntity.ok(eventService.getPositionBySecurityAndAccount(security, account));
    }else{
      return ResponseEntity.notFound().build();
    }
  }
}

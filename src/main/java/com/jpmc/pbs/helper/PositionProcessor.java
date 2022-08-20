package com.jpmc.pbs.helper;

import com.jpmc.pbs.bean.Event;
import com.jpmc.pbs.bean.Position;
import com.jpmc.pbs.enums.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PositionProcessor {
  private static final String UNDER_SCORE = "_";

  @Autowired private Map<String, Position> positionsDataMap;

  public void processEvent(List<Event> eventList) {
    if (!Objects.isNull(eventList)) {
      buildPositions(eventList);
    }
  }

  private void buildPositions(List<Event> eventList) {
    if (!Objects.isNull(eventList)) {
      eventList.stream()
          .filter(e -> !Objects.isNull(e.getId()))
          .forEach(
              event -> {
                StringBuilder key = new StringBuilder();
                key.append(event.getAccount()).append(UNDER_SCORE).append(event.getSecurity());
                if (positionsDataMap.containsKey(key.toString())) {
                  Position position = positionsDataMap.get(key.toString());
                  position.getEvents().add(createEvent(event));
                  getTotalQuantity(position, event);
                } else {
                  positionsDataMap.put(key.toString(), createPosition(event));
                  Position position = positionsDataMap.get(key.toString());
                  position.setQuantity(0L);
                  getTotalQuantity(position, event);
                }
              });
    }
  }

  private Position createPosition(Event event) {
    Position position =
        Position.builder()
            .account(event.getAccount())
            .security(event.getSecurity())
            .events(new ArrayList<>())
            .quantityMap(new HashMap<>())
            .build();
    position.getEvents().add(createEvent(event));
    return position;
  }

  private Event createEvent(Event event) {
    return Event.builder()
        .id(event.getId())
        .action(event.getAction())
        .account(event.getAccount())
        .security(event.getSecurity())
        .quantity(event.getQuantity())
        .build();
  }

  private void getTotalQuantity(Position position, Event event) {
    if (position.getQuantityMap().containsKey(event.getId())) {
      if (Action.BUY.equals(event.getAction())) {
        position.setQuantity(position.getQuantity() + event.getQuantity());
      } else if (Action.CANCEL.equals(event.getAction())) {
        position.setQuantity(position.getQuantity() - position.getQuantityMap().get(event.getId()));
      } else {
        position.setQuantity(position.getQuantity() - event.getQuantity());
      }
    } else {
      if (Action.BUY.equals(event.getAction())) {
        position.getQuantityMap().put(event.getId(), event.getQuantity());
        position.setQuantity(position.getQuantity() + event.getQuantity());
      } else if (Action.SELL.equals(event.getAction())) {
        position.getQuantityMap().put(event.getId(), (event.getQuantity() * -1));
        position.setQuantity(position.getQuantity() - event.getQuantity());
      }
    }
  }

  public List<Position> getPositions() {
    return new ArrayList<>(positionsDataMap.values());
  }

  public Position getPositionBySecurityAndAccount(String security, String account) {
    String key = account + UNDER_SCORE + security;
    return positionsDataMap.getOrDefault(key, null);
  }
}

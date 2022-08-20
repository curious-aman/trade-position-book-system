package com.jpmc.pbs.helper;

import com.jpmc.pbs.bean.Event;
import com.jpmc.pbs.bean.Position;
import com.jpmc.pbs.enums.Action;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PositionProcessorTest {
  List<Event> eventList;
  @Autowired private Map<String, Position> positionsDataMap;
  @Autowired PositionProcessor positionProcessor;

  @Before
  public void before() {
    Event firstEvent =
        Event.builder()
            .id(1L)
            .action(Action.BUY)
            .account("ABC")
            .security("SEC1")
            .quantity(10L)
            .build();
    Event secondEvent =
        Event.builder()
            .id(2L)
            .action(Action.BUY)
            .account("ABC")
            .security("SEC1")
            .quantity(20L)
            .build();
    eventList = new ArrayList<>();
    eventList.add(firstEvent);
    eventList.add(secondEvent);
  }

  @After
  public void after() {
    eventList.clear();
    positionsDataMap.clear();
  }

  @Test
  public void testProcessEventWithWIthEmptyList() {
    List<Event> eventListInner = new ArrayList<>();
    positionProcessor.processEvent(eventListInner);
    List<Position> result = positionProcessor.getPositions();
    assertTrue(result != null && result.isEmpty());
  }

  @Test
  public void testProcessEventWithBuyAction() {
    positionProcessor.processEvent(eventList);
    List<Position> result = positionProcessor.getPositions();
    assertTrue(result != null && !result.isEmpty());
  }

  @Test
  public void testProcessEventWithBuyAndSellAction() {
    Event event =
        Event.builder()
            .id(1L)
            .action(Action.SELL)
            .account("ABC")
            .security("SEC1")
            .quantity(10L)
            .build();
    eventList.add(event);
    positionProcessor.processEvent(eventList);
    List<Position> result = positionProcessor.getPositions();
    assertTrue(result != null && !result.isEmpty());
  }
}

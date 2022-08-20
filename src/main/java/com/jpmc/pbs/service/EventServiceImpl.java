package com.jpmc.pbs.service;

import com.jpmc.pbs.bean.Event;
import com.jpmc.pbs.bean.Position;
import com.jpmc.pbs.helper.PositionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

  @Autowired PositionProcessor positionProcessor;

  @Override
  public void savePosition(List<Event> eventList) {
    positionProcessor.processEvent(eventList);
  }

  @Override
  public List<Position> getPositions() {
    return positionProcessor.getPositions();
  }

  @Override
  public Position getPositionBySecurityAndAccount(String security, String account) {
    return positionProcessor.getPositionBySecurityAndAccount(security, account);
  }
}

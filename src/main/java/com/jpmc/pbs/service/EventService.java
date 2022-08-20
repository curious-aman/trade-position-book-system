package com.jpmc.pbs.service;

import com.jpmc.pbs.bean.Event;
import com.jpmc.pbs.bean.Position;

import java.util.List;

public interface EventService {
  void savePosition(List<Event> eventList);

  List<Position> getPositions();

  Position getPositionBySecurityAndAccount(String security, String account);
}

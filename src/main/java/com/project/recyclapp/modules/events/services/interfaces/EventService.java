package com.project.recyclapp.modules.events.services.interfaces;

import com.project.recyclapp.modules.events.models.Event;
import com.project.recyclapp.modules.events.models.RegisterEvent;
import com.project.recyclapp.modules.users.models.User;

import java.util.List;

public interface EventService {
    Event registerEvent(RegisterEvent registerEvent);

    Event updateEvent(RegisterEvent registerEvent, Integer id);

    Event getEventById(Integer id);

    List<Event> getEventByUser(User user);

    List<Event> getAllEvents();

    String deleteEvent(Event event);
}

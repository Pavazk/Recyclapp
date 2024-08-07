package com.project.recyclapp.modules.events.services.interfaces;

import com.project.recyclapp.modules.events.models.CollectionType;
import com.project.recyclapp.modules.events.models.Event;
import com.project.recyclapp.modules.events.models.EventType;
import com.project.recyclapp.modules.events.models.RegisterEvent;
import com.project.recyclapp.modules.users.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventService {
    Event registerEvent(RegisterEvent registerEvent);

    Event updateEvent(RegisterEvent registerEvent, Integer id);

    Event getEventById(Integer id);

    RegisterEvent getRegisterEvent(Integer id);

    List<Event> getEventByUser(String code);

    List<Event> getEventByOwner(String code);

    List<Event> getAllEvents();

    String deleteEvent(Event event);

    List<CollectionType> getAllCollectionType();

    List<EventType> getAllEventType();
}

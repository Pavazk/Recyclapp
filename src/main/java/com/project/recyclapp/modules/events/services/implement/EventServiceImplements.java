package com.project.recyclapp.modules.events.services.implement;

import com.project.recyclapp.commons.Utils;
import com.project.recyclapp.commons.exceptions.CustomException;
import com.project.recyclapp.modules.events.models.Event;
import com.project.recyclapp.modules.events.models.EventsParticipant;
import com.project.recyclapp.modules.events.models.RegisterEvent;
import com.project.recyclapp.modules.events.repository.CollectionTypeRepository;
import com.project.recyclapp.modules.events.repository.EventParticipantsRepository;
import com.project.recyclapp.modules.events.repository.EventRepository;
import com.project.recyclapp.modules.events.repository.EventTypeRepository;
import com.project.recyclapp.modules.events.services.interfaces.EventService;
import com.project.recyclapp.modules.users.models.User;
import com.project.recyclapp.modules.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImplements implements EventService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventTypeRepository eventTypeRepository;
    @Autowired
    CollectionTypeRepository collectionTypeRepository;
    @Autowired
    EventParticipantsRepository eventParticipantsRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Event registerEvent(RegisterEvent registerEvent) {
        Event event = new Event();
        event.setName(registerEvent.getName());
        event.setDescription(registerEvent.getDescription());
        event.setEventType(registerEvent.getEventType());
        event.setCollectionType(registerEvent.getCollectionType());
        event = eventRepository.save(event);
        for (User user : registerEvent.getParticipants()) {
            EventsParticipant eventsParticipant = new EventsParticipant();
            eventsParticipant.setEvents(event);
            eventsParticipant.setUser(user);
            eventParticipantsRepository.save(eventsParticipant);
        }
        return event;
    }

    @Override
    public Event updateEvent(RegisterEvent registerEvent, Integer id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new CustomException("Evento no existe");
        }
        if (Utils.isNotNullOrEmptyWithTrim(registerEvent.getName())) {
            event.get().setName(registerEvent.getName());
        }
        if (Utils.isNotNullOrEmptyWithTrim(registerEvent.getDescription())) {
            event.get().setDescription(registerEvent.getDescription());
        }
        if (!registerEvent.getParticipants().isEmpty()) {
            //eventParticipantsRepository.deleteAllByEvent(event.get());
            for (User user : registerEvent.getParticipants()) {
                EventsParticipant eventsParticipant = new EventsParticipant();
                eventsParticipant.setEvents(event.get());
                eventsParticipant.setUser(user);
                eventParticipantsRepository.save(eventsParticipant);
            }
        }
        return eventRepository.save(event.get());
    }

    @Override
    public Event getEventById(Integer id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new CustomException("Evento no existe");
        }
        return event.get();
    }
    
    @Override
    public List<Event> getEventByUser(User user) {
        return eventRepository.findAllEventByUser(user);
    }

    @Override
    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @Override
    public String deleteEvent(Event event) {
        eventRepository.delete(event);
        return "Evento eliminado con exito";
    }
}

package com.project.recyclapp.modules.events.services.implement;

import com.project.recyclapp.commons.Utils;
import com.project.recyclapp.commons.exceptions.CustomException;
import com.project.recyclapp.modules.events.models.*;
import com.project.recyclapp.modules.events.repository.CollectionTypeRepository;
import com.project.recyclapp.modules.events.repository.EventParticipantsRepository;
import com.project.recyclapp.modules.events.repository.EventRepository;
import com.project.recyclapp.modules.events.repository.EventTypeRepository;
import com.project.recyclapp.modules.events.services.interfaces.EventService;
import com.project.recyclapp.modules.users.models.User;
import com.project.recyclapp.modules.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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
        event.setOwner(userRepository.findByCode(registerEvent.getCode_owner()).get());
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
            eventParticipantsRepository.deleteAllByEvents(event.get());
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
    public RegisterEvent getRegisterEvent(Integer id) {
        RegisterEvent registerEvent = new RegisterEvent();
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new CustomException("Evento no existe");
        }
        registerEvent.setName(event.get().getName());
        registerEvent.setDescription(event.get().getDescription());
        registerEvent.setEventType(event.get().getEventType());
        registerEvent.setCollectionType(event.get().getCollectionType());
        registerEvent.setCode_owner(event.get().getOwner().getCode());
        List<User> participants = new ArrayList<>();
        for (EventsParticipant eventsParticipant : eventParticipantsRepository.findAllByEvents(event.get())) {
            participants.add(eventsParticipant.getUser());
        }
        registerEvent.setParticipants(participants);
        return registerEvent;
    }

    @Override
    public List<Event> getEventByUser(String code) {
        List<EventsParticipant> participants = eventParticipantsRepository.findAllByUser(userRepository.findByCode(code).get());
        return participants.stream().map(EventsParticipant::getEvents).collect(Collectors.toList());

    }

    @Override
    public List<Event> getEventByOwner(String code) {
        Optional<User> user = userRepository.findByCode(code);
        if(user.isEmpty()){
            throw new CustomException("Usuario no encontrado");
        }
        return eventRepository.findAllEventByOwner(user.get());
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

    @Override
    public List<CollectionType> getAllCollectionType() {
        return (List<CollectionType>) collectionTypeRepository.findAll();
    }

    @Override
    public List<EventType> getAllEventType() {
        return (List<EventType>) eventTypeRepository.findAll();
    }
}

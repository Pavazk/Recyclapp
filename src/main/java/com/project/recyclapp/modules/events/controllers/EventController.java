package com.project.recyclapp.modules.events.controllers;

import com.project.recyclapp.modules.events.models.CollectionType;
import com.project.recyclapp.modules.events.models.Event;
import com.project.recyclapp.modules.events.models.EventType;
import com.project.recyclapp.modules.events.models.RegisterEvent;
import com.project.recyclapp.modules.events.services.interfaces.EventService;
import com.project.recyclapp.modules.users.models.User;
import com.project.recyclapp.modules.users.models.update.UserUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/register")
    public ResponseEntity<Event> registerEvent(@RequestBody @Valid RegisterEvent event) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.registerEvent(event));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Event> updateUser(@RequestBody @Valid RegisterEvent event, @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(eventService.updateEvent(event,id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(eventService.getEventById(id));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Event>> getEventByUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(eventService.getEventByUser(user));
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(eventService.getAllEvents());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteEvent(@RequestBody Event event){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(eventService.deleteEvent(event));
    }

    @GetMapping("/collection")
    public ResponseEntity<List<CollectionType>> getAllCollectionType() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(eventService.getAllCollectionType());
    }

    @GetMapping("/type")
    public ResponseEntity<List<EventType>> getAllEventType() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(eventService.getAllEventType());
    }

}

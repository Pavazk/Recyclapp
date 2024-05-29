package com.project.recyclapp.modules.events.repository;

import com.project.recyclapp.modules.events.models.Event;
import com.project.recyclapp.modules.events.models.EventsParticipant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventParticipantsRepository extends CrudRepository<EventsParticipant, Integer> {

    void deleteByEvents(Event event);

    List<EventsParticipant> findAllByEvents(Event event);
}

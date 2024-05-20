package com.project.recyclapp.modules.events.repository;

import com.project.recyclapp.modules.events.models.Event;
import com.project.recyclapp.modules.events.models.EventsParticipant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventParticipantsRepository extends CrudRepository<EventsParticipant, Integer> {
    void deleteAllByEvent(Event event);
}

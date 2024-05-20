package com.project.recyclapp.modules.events.repository;

import com.project.recyclapp.modules.events.models.Event;
import com.project.recyclapp.modules.users.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

    @Query("select e from Event e join e.eventsParticipants p where p.user = : user")
    List<Event> findAllEventByUser(@Param("user") User user);
}

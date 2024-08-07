package com.project.recyclapp.modules.events.repository;

import com.project.recyclapp.modules.events.models.EventType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository extends CrudRepository<EventType, Integer> {
}

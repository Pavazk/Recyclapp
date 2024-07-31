package com.project.recyclapp.modules.events.repository;


import com.project.recyclapp.modules.events.models.CollectionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionTypeRepository extends CrudRepository<CollectionType, Integer> {
}

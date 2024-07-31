package com.project.recyclapp.modules.users.repository;

import com.project.recyclapp.modules.users.models.UserType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserTypeRepository extends CrudRepository<UserType, Integer> {
    Optional<UserType> findByName(String name);
}

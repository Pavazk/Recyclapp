package com.project.recyclapp.user.repository;

import com.project.recyclapp.user.models.UserType;
import org.springframework.data.repository.CrudRepository;

public interface UserTypeRepository extends CrudRepository<UserType, Integer> {
}

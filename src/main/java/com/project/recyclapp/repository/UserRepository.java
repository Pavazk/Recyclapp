package com.project.recyclapp.repository;

import com.project.recyclapp.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}

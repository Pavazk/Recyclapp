package com.project.recyclapp.users.repository;

import com.project.recyclapp.users.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByCode(String code);

    Optional<User> findByEmail(String email);

    //select * from user where email = "?" and code != "?"
    Optional<User> findByEmailAndCodeNot(String email, String code);
    //todo: buscarle uso a esta funcion
}

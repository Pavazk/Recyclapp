package com.project.recyclapp.modules.users.repository;

import com.project.recyclapp.modules.users.models.User;
import com.project.recyclapp.modules.users.models.UserType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByCode(String code);

    Optional<User> findByEmail(String email);
    List<User> findByUserType(UserType userType);

    //select * from user where email = "?" and code != "?"
    Optional<User> findByEmailAndCodeNot(String email, String code);
    //todo: buscarle uso a esta funcion
    User findByCodeContaining(String code);
}

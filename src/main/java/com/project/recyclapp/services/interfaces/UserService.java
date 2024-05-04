package com.project.recyclapp.services.interfaces;

import com.project.recyclapp.models.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(long id);

    User updateUser(User user, Long id);

    void deleteUser(long id);

    List<User> getAllUsers();
}

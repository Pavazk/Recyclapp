package com.project.recyclapp.user.services.interfaces;

import com.project.recyclapp.user.models.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserByEmail(String email);

    User updateUser(User user, String code);

    void deleteUser(String code);

    List<User> getAllUsers();
}

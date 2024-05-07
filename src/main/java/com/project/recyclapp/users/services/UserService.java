package com.project.recyclapp.users.services;

import com.project.recyclapp.users.models.User;
import com.project.recyclapp.users.models.login.UserLogin;
import com.project.recyclapp.users.models.update.UserUpdate;

import java.util.List;

public interface UserService {

    User registerUser(User user);

    User updateUser(UserUpdate userUpdate);

    User loginUser(UserLogin userLogin);

    User getUserByEmail(String email);

    User updateUser(User user, String code);

    List<User> getAllUsers();
}

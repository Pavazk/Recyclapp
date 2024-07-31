package com.project.recyclapp.modules.users.services;

import com.project.recyclapp.modules.users.models.login.UserLogin;
import com.project.recyclapp.modules.users.models.update.UserUpdate;
import com.project.recyclapp.modules.users.models.User;

import java.util.List;

public interface UserService {

    User registerUser(User user);

    User updateUser(UserUpdate userUpdate);

    User loginUser(UserLogin userLogin);

    User getUserByEmail(String email);

    User updateUser(User user, String code);

    List<User> getAllUsers();

    User getUserByCode(String code);
}

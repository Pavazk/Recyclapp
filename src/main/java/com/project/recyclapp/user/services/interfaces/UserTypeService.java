package com.project.recyclapp.user.services.interfaces;

import com.project.recyclapp.user.models.UserType;

import java.util.List;

public interface UserTypeService {

    UserType createUserType(UserType userType);

    UserType getUserTypeById(Integer id);

    UserType updateUserType(UserType userType, Integer id);

    void deleteUserType(Integer id);

    List<UserType> getAllUserType();
}

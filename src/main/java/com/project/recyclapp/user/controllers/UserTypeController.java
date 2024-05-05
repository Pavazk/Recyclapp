package com.project.recyclapp.user.controllers;

import com.project.recyclapp.user.models.User;
import com.project.recyclapp.user.models.UserType;
import com.project.recyclapp.user.services.interfaces.UserTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user_type")
public class UserTypeController {

    @Autowired
    private UserTypeService userTypeService;

    @PostMapping
    public UserType createUser(@RequestBody @Valid UserType userType) {
        return userTypeService.createUserType(userType);
    }

    @PutMapping({"/{id}"})
    public UserType getUserByEmail(@PathVariable Integer id) {
        return userTypeService.getUserTypeById(id);
    }

    @PostMapping({"/{id}"})
    public UserType updateUser(@RequestBody UserType userType, @PathVariable Integer id) {
        return userTypeService.updateUserType(userType, id);
    }

    @GetMapping
    public List<UserType> getAllUserType() {
        return userTypeService.getAllUserType();
    }


}

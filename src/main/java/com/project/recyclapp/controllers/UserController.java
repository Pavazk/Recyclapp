package com.project.recyclapp.controllers;

import com.project.recyclapp.models.User;
import com.project.recyclapp.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        return userService.createUser(user);
    }

    @PutMapping({"/{id}"})
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping({"/{id}"})
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        return userService.updateUser(user, id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


}

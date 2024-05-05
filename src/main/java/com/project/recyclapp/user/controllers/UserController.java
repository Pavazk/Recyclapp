package com.project.recyclapp.user.controllers;

import com.project.recyclapp.user.models.User;
import com.project.recyclapp.user.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping({"/{email}"})
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping({"/{email}"})
    public User updateUser(@RequestBody User user, @PathVariable String email) {
        return userService.updateUser(user, email);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


}

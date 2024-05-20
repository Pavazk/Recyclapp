package com.project.recyclapp.modules.users.controllers;

import com.project.recyclapp.modules.users.models.User;
import com.project.recyclapp.modules.users.models.login.UserLogin;
import com.project.recyclapp.modules.users.services.UserService;
import com.project.recyclapp.modules.users.models.update.UserUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(user));
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserUpdate user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody @Valid UserLogin user) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.loginUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getAllUsers());
    }

    @GetMapping("/{code}")
    public ResponseEntity<User> getUserByCode(@PathVariable @Size(min = 1, max = 6, message = "Por favor ingrese un código valido") String code) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getUserByCode(code));
    }
}

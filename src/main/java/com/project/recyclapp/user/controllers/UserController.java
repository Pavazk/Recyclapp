package com.project.recyclapp.user.controllers;

import com.project.recyclapp.user.models.User;
import com.project.recyclapp.user.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping({"/{email}"})
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email) {
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getUserByEmail(email));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping({"/{email}"})
    public ResponseEntity<Object> updateUser(@RequestBody User user, @PathVariable String email) {
        try{
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(user, email));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    //@GetMapping todo: validar error
    public ResponseEntity<List<Object>> getAllUsers() {
        try{
            List<User> usersList = userService.getAllUsers();
            List<Object> objetosList = new ArrayList<>();
            objetosList.addAll(usersList);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(objetosList);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(List.of(e.getMessage()));
        }
    }


}

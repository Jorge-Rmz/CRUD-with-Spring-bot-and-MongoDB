package com.example.spring_mongo.controllers;


import com.example.spring_mongo.models.User;
import com.example.spring_mongo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/all/user")
    public ResponseEntity<List<User>> getUser(){
        return userService.getAllUser();
    }

    @PostMapping("/newUser")
    public ResponseEntity<?> postUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/update/{id}/user")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user){
        return userService.updateUser(id,user);
    }
}

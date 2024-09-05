package com.example.spring_mongo.controllers;


import com.example.spring_mongo.models.User;
import com.example.spring_mongo.services.UserService;
import com.example.spring_mongo.tdo.GeneralResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/all/user")
    public ResponseEntity<GeneralResponse> getUser(){
        return userService.getAllUser();
    }

    @GetMapping("/find/{id}/user")
    public ResponseEntity<GeneralResponse> findById(@PathVariable String id){
        return userService.getById(id);
    }

    @PostMapping("/newUser")
    public ResponseEntity<GeneralResponse> postUser(@Valid @RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/update/{id}/user")
    public ResponseEntity<GeneralResponse> updateUser(@PathVariable String id, @Valid @RequestBody User user){
        return userService.updateUser(id,user);
    }

    @DeleteMapping("/delete/{id}/user")
    public ResponseEntity<GeneralResponse> deleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }
}

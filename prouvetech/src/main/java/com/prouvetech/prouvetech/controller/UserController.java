package com.prouvetech.prouvetech.controller;

import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    public UserController() {
    }

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("details/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/login")
    public String login(@Validated @RequestBody User user) {
        String token = userService.authenticateUser(user.getMail(), user.getPassword());
        return token;
    }

}

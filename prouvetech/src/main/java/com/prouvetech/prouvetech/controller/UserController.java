package com.prouvetech.prouvetech.controller;

import com.prouvetech.prouvetech.model.Status;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.service.StatusService;
import com.prouvetech.prouvetech.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    public UserController() {
    }

    @Autowired
    private UserService userService;

    @Autowired
    private StatusService statusService;

    @PostMapping("/register")
    public ResponseEntity<String> addUser(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("mail") String mail,
            @RequestParam("password") String password,
            @RequestParam("statusId") Long statusId) {

        Status status = this.statusService.getStatus(statusId);

        User user = new User(firstname, lastname, mail, password, status);
        String response = userService.createUser(user);

        if (response.equals("Vous êtes inscrit")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("details/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Validated @RequestParam("mail") String mail,
            @RequestParam("password") String password) {

        User user = new User(mail, password);
        String token = userService.authenticateUser(user.getMail(), user.getPassword());

        // Vérifie si c'est un token (par exemple commence par "eyJ" dans un JWT)
        if (token != null && token.startsWith("eyJ")) {
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(token, HttpStatus.UNAUTHORIZED);
        }
    }

}

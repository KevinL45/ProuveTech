package com.prouvetech.prouvetech.controller;

import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.model.UserSocialNetworks;
import com.prouvetech.prouvetech.service.UserService;
import com.prouvetech.prouvetech.service.UserSocialNetworksService;
import com.prouvetech.prouvetech.utils.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usersocialnetworks")
public class UserSocialNetworksController {

    @Autowired
    private UserSocialNetworksService service;

    @Autowired
    private UserService userService;

    public UserSocialNetworksController(UserSocialNetworksService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<String> addSocialNetwork(
            @RequestParam Long socialNetworkId,
            @RequestParam String profileUrl) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByMail(email);

        ResponseMessage result = service.addSocialNetwork(user.getId(), socialNetworkId, profileUrl);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @PutMapping("/update/{socialNetworkId}")
    public ResponseEntity<String> updateProfileUrl(
            @PathVariable Long socialNetworkId,
            @RequestParam String profileUrl) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByMail(email);

        ResponseMessage result = service.updateProfileUrl(user.getId(), socialNetworkId, profileUrl);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getMessage());
        }

        return ResponseEntity.ok(result.getMessage());
    }

    @DeleteMapping("/delete/{socialNetworkId}")
    public ResponseEntity<String> deleteSocialNetwork(
            @PathVariable Long socialNetworkId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByMail(email);

        ResponseMessage result = service.deleteSocialNetwork(user.getId(), socialNetworkId);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getMessage());
        }

        return ResponseEntity.ok(result.getMessage());
    }

    @GetMapping("/user/")
    public List<UserSocialNetworks> getUserSocialNetworks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByMail(email);
        return service.getUserSocialNetworks(user.getId());
    }
}

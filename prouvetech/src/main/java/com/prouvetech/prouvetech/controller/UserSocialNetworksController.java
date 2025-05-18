package com.prouvetech.prouvetech.controller;

import com.prouvetech.prouvetech.model.UserSocialNetworks;
import com.prouvetech.prouvetech.service.UserSocialNetworksService;
import com.prouvetech.prouvetech.utils.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usersocialnetworks")
public class UserSocialNetworksController {

    private final UserSocialNetworksService service;

    public UserSocialNetworksController(UserSocialNetworksService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<String> addSocialNetwork(
            @RequestParam Long userId,
            @RequestParam Long socialNetworkId,
            @RequestParam String profileUrl) {

        ResponseMessage result = service.addSocialNetwork(userId, socialNetworkId, profileUrl);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @PutMapping("/update/{userId}/{socialNetworkId}")
    public ResponseEntity<String> updateProfileUrl(
            @PathVariable Long userId,
            @PathVariable Long socialNetworkId,
            @RequestParam String profileUrl) {

        ResponseMessage result = service.updateProfileUrl(userId, socialNetworkId, profileUrl);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getMessage());
        }

        return ResponseEntity.ok(result.getMessage());
    }

    @DeleteMapping("/delete/{userId}/{socialNetworkId}")
    public ResponseEntity<String> deleteSocialNetwork(
            @PathVariable Long userId,
            @PathVariable Long socialNetworkId) {

        ResponseMessage result = service.deleteSocialNetwork(userId, socialNetworkId);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getMessage());
        }

        return ResponseEntity.ok(result.getMessage());
    }

    @GetMapping("/user/{userId}")
    public List<UserSocialNetworks> getUserSocialNetworks(@PathVariable Long userId) {
        return service.getUserSocialNetworks(userId);
    }
}

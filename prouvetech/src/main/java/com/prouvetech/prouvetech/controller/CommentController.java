package com.prouvetech.prouvetech.controller;

import com.prouvetech.prouvetech.utils.ResponseMessage;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.service.CommentService;
import com.prouvetech.prouvetech.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> addComment(
            @RequestParam Long projectId,
            @RequestParam String content) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByMail(email);

        ResponseMessage result = commentService.addComment(user.getId(), projectId, content);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @PutMapping("/update/{projectId}")
    public ResponseEntity<String> updateComment(
            @PathVariable Long projectId,
            @RequestParam String content) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByMail(email);

        ResponseMessage result = commentService.updateComment(user.getId(), projectId, content);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long projectId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByMail(email);

        ResponseMessage result = commentService.deleteComment(user.getId(), projectId);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }
}

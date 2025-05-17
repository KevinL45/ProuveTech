package com.prouvetech.prouvetech.controller;

import com.prouvetech.prouvetech.utils.ResponseMessage;
import com.prouvetech.prouvetech.service.CommentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> addComment(
            @RequestParam Long userId,
            @RequestParam Long projectId,
            @RequestParam String content) {

        ResponseMessage result = commentService.addComment(userId, projectId, content);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @PutMapping("/update/{userId}/{projectId}")
    public ResponseEntity<String> updateComment(
            @PathVariable Long userId,
            @PathVariable Long projectId,
            @RequestParam String content) {
        ResponseMessage result = commentService.updateComment(userId, projectId, content);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @DeleteMapping("/delete/{userId}/{projectId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long userId,
            @PathVariable Long projectId) {
        ResponseMessage result = commentService.deleteComment(userId, projectId);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }
}

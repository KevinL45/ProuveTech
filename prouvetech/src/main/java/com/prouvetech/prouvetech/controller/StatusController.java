package com.prouvetech.prouvetech.controller;

import com.prouvetech.prouvetech.model.Status;
import com.prouvetech.prouvetech.service.StatusService;
import com.prouvetech.prouvetech.utils.ResponseMessage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @PostMapping("/add")
    public ResponseEntity<String> addStatus(@RequestParam("name") String name) {
        Status status = new Status(name);
        ResponseMessage result = statusService.createStatus(status);
        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @GetMapping("/details/{id}")
    public Status getStatus(@PathVariable Long id) {
        return statusService.getStatus(id);
    }

    @GetMapping("/list")
    public List<Status> getAllStatus() {
        return statusService.getAllStatus();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam("name") String name) {
        Status status = new Status(name);
        ResponseMessage result = statusService.updateStatus(id, status);
        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStatus(@PathVariable Long id) {
        ResponseMessage result = statusService.deleteStatus(id);
        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }
}

package com.prouvetech.prouvetech.controller;

import com.prouvetech.prouvetech.model.Status;
import com.prouvetech.prouvetech.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
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
        String result = statusService.createStatus(status);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Status> getStatus(@PathVariable Long id) {
        return ResponseEntity.ok(statusService.getStatus(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam("name") String name) {
        Status status = new Status(name);
        String result = statusService.updateStatus(id, status);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStatus(@PathVariable Long id) {
        String result = statusService.deleteStatus(id);
        return ResponseEntity.ok(result);
    }
}

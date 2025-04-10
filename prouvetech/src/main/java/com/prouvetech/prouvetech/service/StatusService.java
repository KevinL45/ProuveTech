package com.prouvetech.prouvetech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.Status;
import com.prouvetech.prouvetech.repository.StatusRepository;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public Status getStatus(Long id) {
        return statusRepository.findById(id).orElseThrow(() -> new RuntimeException("Aucun utilisateur"));
    }

}

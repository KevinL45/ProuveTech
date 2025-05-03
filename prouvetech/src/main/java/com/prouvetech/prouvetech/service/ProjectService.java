package com.prouvetech.prouvetech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.Project;
import com.prouvetech.prouvetech.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project getProject(long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun projet trouvé avec l'ID : " + id));
    }
}

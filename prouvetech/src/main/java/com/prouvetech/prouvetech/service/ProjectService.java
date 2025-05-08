package com.prouvetech.prouvetech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.Project;
import com.prouvetech.prouvetech.repository.ProjectRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project getProject(long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun projet trouvé avec l'ID : " + id));
    }

    public List<Project> getAllProject() {
        return projectRepository.findAll();
    }

    public ResponseMessage addProject(Project project) {
        projectRepository.save(project);
        return new ResponseMessage(true, "Le projet a été ajouté");
    }

    public ResponseMessage updateProject(Project project, Long id) {
        Project projectExsist = getProject(id);

        projectExsist.setUpdateDate(project.getUpdateDate());
        projectExsist.setName(project.getName());
        projectExsist.setDescription(project.getDescription());
        projectExsist.setSourceCode(project.getSourceCode());
        projectExsist.setVideo(project.getVideo());
        projectExsist.setUser(project.getUser());

        projectRepository.save(project);

        return new ResponseMessage(true, "Le projet a été modifié");

    }

    public ResponseMessage deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            return new ResponseMessage(false, "Aucun projet trouvé avec l'ID : " + id);
        }
        projectRepository.deleteById(id);
        return new ResponseMessage(true, "Le project a été supprimé avec succès.");
    }
}

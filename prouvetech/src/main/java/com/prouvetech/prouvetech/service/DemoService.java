package com.prouvetech.prouvetech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.Demo;
import com.prouvetech.prouvetech.model.Project;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.repository.DemoRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;

@Service
public class DemoService {

    @Autowired
    private DemoRepository demoRepository;

    public Demo getDemo(long id) {
        return demoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucune vidéo trouvé avec l'ID : " + id));
    }

    public List<Demo> getMyDemos(User user) {
        return demoRepository.findByProjectUser(user);
    }

    public List<Demo> getAllDemos() {
        return demoRepository.findAll();
    }

    public long countDemosByUser(com.prouvetech.prouvetech.model.User user) {
        return demoRepository.countByProjectUser(user);
    }

    public long countDemosByProject(Project project) {
        return demoRepository.countByProject(project);
    }

    public ResponseMessage addDemo(Demo demo) {

        demoRepository.save(demo);
        return new ResponseMessage(true,
                "La vidéo '" + demo.getName() + "' a été ajouté");
    }

    public ResponseMessage updateDemo(Demo demo) {

        demoRepository.save(demo);
        return new ResponseMessage(true, "Le demo '" + demo.getName() + "'a été modifié");

    }

    public ResponseMessage deleteDemo(Long id) {
        Demo demo = getDemo(id);
        if (!demoRepository.existsById(id)) {
            return new ResponseMessage(false, "Aucune vidéo trouvé avec l'ID : " + demo.getId());
        }
        demoRepository.deleteById(id);
        return new ResponseMessage(true,
                "La vidéo '" + demo.getName() + "'' avec l'ID " + demo.getId() + ", a été supprimé avec succès.");
    }

    public List<Demo> getDemosByProjectId(Long projectId) {
        return demoRepository.findByProjectId(projectId);
    }

    public List<Demo> searchDemos(String name) {
        if (name != null) {
            return demoRepository.findByNameContainingIgnoreCase(name);
        } else {
            return demoRepository.findAll();
        }
    }

}

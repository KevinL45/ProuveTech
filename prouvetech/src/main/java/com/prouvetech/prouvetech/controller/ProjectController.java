package com.prouvetech.prouvetech.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prouvetech.prouvetech.model.Project;
import com.prouvetech.prouvetech.service.ProjectService;
import com.prouvetech.prouvetech.service.UserService;
import com.prouvetech.prouvetech.utils.ResponseMessage;
import com.prouvetech.prouvetech.utils.Uploads;

@RestController
@RequestMapping("/project")
public class ProjectController {

    public ProjectController() {

    }

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private Uploads uploads;

    @PostMapping("/create")
    public ResponseEntity<String> addProject(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("sourcecode") String sourcecode,
            @RequestParam("video") MultipartFile video,
            @RequestParam("userId") Long userId) throws IOException {

        Tika tika = new Tika();

        String detectedType = tika.detect(video.getInputStream());

        if (!detectedType.startsWith("video/")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être une vidéo");
        }

        String newVideo = uploads.saveFile(video);
        Project project = new Project(name, description, newVideo, sourcecode, userService.getUser(userId),
                LocalDateTime.now(),
                LocalDateTime.now());

        ResponseMessage result = projectService.addProject(project);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSocialNetworks(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("sourcecode") String sourcecode,
            @RequestParam("video") MultipartFile video,
            @RequestParam("userId") Long userId) throws IOException {

        Tika tika = new Tika();

        String detectedType = tika.detect(video.getInputStream());

        if (!detectedType.startsWith("video/")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être une vidéo");
        }

        Project projectExist = projectService.getProject(id);
        String newVideo = uploads.updateFile(video, projectExist.getVideo());
        Project project = new Project(name, description, newVideo, sourcecode, userService.getUser(userId),
                LocalDateTime.now());

        ResponseMessage result = this.projectService.updateProject(project, id);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @GetMapping("/list")
    public List<Project> listProjects() {
        return projectService.getAllProject();
    }

    @GetMapping("/details/{id}")
    public Project getProject(@PathVariable Long id) {
        return projectService.getProject(id);
    }

}

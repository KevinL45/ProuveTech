package com.prouvetech.prouvetech.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prouvetech.prouvetech.dto.StatusDTO;
import com.prouvetech.prouvetech.dto.UserDTO;
import com.prouvetech.prouvetech.model.Demo;
import com.prouvetech.prouvetech.model.Project;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.service.DemoService;
import com.prouvetech.prouvetech.service.ProjectService;
import com.prouvetech.prouvetech.service.UserService;
import com.prouvetech.prouvetech.utils.ResponseMessage;
import com.prouvetech.prouvetech.utils.Uploads;
import com.prouvetech.prouvetech.dto.DemoDTO;
import com.prouvetech.prouvetech.dto.ProjectDTO;

@RestController
@RequestMapping("/demo")
public class DemoController {

    public DemoController() {

    }

    @Autowired
    private DemoService demoService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private Uploads uploads;

    private Tika tika = new Tika();

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> addDemo(@ModelAttribute DemoDTO demoDTO) throws IOException {

        String detectedType = tika.detect(demoDTO.getVideo().getInputStream());
        // Verfie si la video est bien un fichier video
        if (!detectedType.startsWith("video/")) {
            ResponseMessage message = new ResponseMessage(false, "Le fichier n'est pas une vidéo");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        // Verifie la durée et la taille de la vidéo
        ResponseMessage validate = uploads.validateVideo(demoDTO.getVideo());
        if (!validate.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validate);
        }

        Project project = projectService.getProject(demoDTO.getProject().getId());
        String newVideo = uploads.saveFile(demoDTO.getVideo());
        String newThumbnail = uploads.saveFile(demoDTO.getThumbnail());

        Demo demo = new Demo(
                demoDTO.getName(),
                demoDTO.getDescription(),
                newThumbnail,
                newVideo,
                project,
                LocalDateTime.now(),
                LocalDateTime.now());

        ResponseMessage result = demoService.addDemo(demo);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseMessage> updateDemo(
            @PathVariable Long id,
            @ModelAttribute DemoDTO demoDTO) throws IOException {

        Project project = projectService.getProject(id);
        Demo demo = demoService.getDemo(id);

        // Verifie la durée et la taille de la vidéo
        ResponseMessage validate = uploads.validateVideo(demoDTO.getVideo());
        if (!validate.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validate);
        }

        if (demoDTO.getVideo() != null) {
            String newVideo = uploads.updateFile(demoDTO.getVideo(),
                    demo.getVideo());
            demo.setVideo(newVideo);
        }

        if (demoDTO.getThumbnail() != null) {
            // Met à jour la miniature si un nouveau fichier est fourni
            String newThumbnail = uploads.updateFile(demoDTO.getThumbnail(), demo.getThumbnail());
            demo.setThumbnail(newThumbnail);
        }

        demo.setName(demoDTO.getName());
        demo.setDescription(demoDTO.getDescription());
        project.setUpdateDate(LocalDateTime.now());

        ResponseMessage result = this.projectService.updateProject(project);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/list")
    public ResponseEntity<List<DemoDTO>> listDemos() {

        List<Demo> demos = demoService.getAllDemos();
        List<DemoDTO> demos_dto = demos.stream()
                .map(d -> new DemoDTO(
                        d.getId(),
                        d.getName(),
                        d.getDescription(),
                        d.getThumbnail(),
                        d.getVideo(),
                        new ProjectDTO(
                                d.getProject().getId(),
                                d.getProject().getName(),
                                d.getProject().getDescription(),
                                d.getProject().getThumbnail(),
                                d.getProject().getSourceCode(),
                                new UserDTO(
                                        d.getProject().getUser().getId(),
                                        d.getProject().getUser().getFirstname(),
                                        d.getProject().getUser().getLastname(),
                                        d.getProject().getUser().getMail(),
                                        d.getProject().getUser().getTitle(),
                                        d.getProject().getUser().getDescription(),
                                        new StatusDTO(
                                                d.getProject().getUser().getStatus().getId(),
                                                d.getProject().getUser().getStatus().getName()),
                                        d.getProject().getUser().getPhoto()))))
                .toList();
        return ResponseEntity.ok(demos_dto);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<DemoDTO> getDemo(@PathVariable Long id) {

        Demo p = demoService.getDemo(id);
        DemoDTO demoDTO = new DemoDTO(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getThumbnail(),
                p.getVideo(),
                new ProjectDTO(
                        p.getProject().getId(),
                        p.getProject().getName(),
                        p.getProject().getDescription(),
                        p.getProject().getThumbnail(),
                        p.getProject().getSourceCode(),
                        new UserDTO(
                                p.getProject().getUser().getId(),
                                p.getProject().getUser().getFirstname(),
                                p.getProject().getUser().getLastname(),
                                p.getProject().getUser().getMail(),
                                p.getProject().getUser().getTitle(),
                                p.getProject().getUser().getDescription(),
                                new StatusDTO(
                                        p.getProject().getUser().getStatus().getId(),
                                        p.getProject().getUser().getStatus().getName()),
                                p.getProject().getUser().getPhoto())));
        return ResponseEntity.ok(demoDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<DemoDTO>> searchDemo(
            @RequestParam(required = false) String name) {

        List<Demo> demo = demoService.searchDemos(name);
        List<DemoDTO> demos_dto = demo.stream()
                .map(d -> new DemoDTO(
                        d.getId(),
                        d.getName(),
                        d.getDescription(),
                        d.getThumbnail(),
                        d.getVideo(),
                        new ProjectDTO(
                                d.getProject().getId(),
                                d.getProject().getName(),
                                d.getProject().getDescription(),
                                d.getProject().getThumbnail(),
                                d.getProject().getSourceCode(),
                                new UserDTO(
                                        d.getProject().getUser().getId(),
                                        d.getProject().getUser().getFirstname(),
                                        d.getProject().getUser().getLastname(),
                                        d.getProject().getUser().getMail(),
                                        d.getProject().getUser().getTitle(),
                                        d.getProject().getUser().getDescription(),
                                        new StatusDTO(
                                                d.getProject().getUser().getStatus().getId(),
                                                d.getProject().getUser().getStatus().getName()),
                                        d.getProject().getUser().getPhoto()))))
                .toList();
        return ResponseEntity.ok(demos_dto);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteDemo(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userService.getUserByMail(email);

        Demo demo = demoService.getDemo(id);

        boolean isOwner = demo.getProject().getUser().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(r -> "ROLE_ADMIN".equals(r.getName()));

        if (!isOwner && !isAdmin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseMessage(false,
                            "Vous n’êtes pas autorisé à supprimer la vidéo."));
        }

        uploads.deleteFile(demo.getThumbnail());
        ResponseMessage result = projectService.deleteProject(demo.getId());

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        return ResponseEntity.ok(result);
    }

}

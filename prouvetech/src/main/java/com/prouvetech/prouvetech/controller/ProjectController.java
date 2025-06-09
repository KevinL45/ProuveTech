package com.prouvetech.prouvetech.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prouvetech.prouvetech.dto.ProjectDTO;
import com.prouvetech.prouvetech.model.Comment;
import com.prouvetech.prouvetech.model.Document;
import com.prouvetech.prouvetech.model.Project;
import com.prouvetech.prouvetech.model.Tool;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.service.CommentService;
import com.prouvetech.prouvetech.service.DocumentService;
import com.prouvetech.prouvetech.service.ProjectService;
import com.prouvetech.prouvetech.service.ToolService;
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
    private DocumentService documentService;

    @Autowired
    private ToolService toolService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private Uploads uploads;

    @PostMapping("/create")
    public ResponseEntity<String> addProject(@ModelAttribute ProjectDTO projectDTO) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByMail(email);

        Tika tika = new Tika();

        String detectedType = tika.detect(projectDTO.getVideo().getInputStream());

        if (!detectedType.startsWith("video/")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être une vidéo");
        }

        List<Document> savedDocuments = new ArrayList<>();
        for (MultipartFile doc : projectDTO.getDocuments()) {
            String type = tika.detect(doc.getInputStream());
            if (type.startsWith("application/pdf")) {
                String path = uploads.saveFile(doc);
                Document document = new Document(doc.getOriginalFilename(), path);
                documentService.createDocument(document);
                savedDocuments.add(document);
            }
        }

        String newVideo = uploads.saveFile(projectDTO.getVideo());
        List<Tool> selectedTools = toolService.getToolsByIds(projectDTO.getTools());

        Project project = new Project(
                projectDTO.getName(),
                projectDTO.getDescription(),
                newVideo,
                projectDTO.getSourcecode(),
                user,
                savedDocuments,
                selectedTools,
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
            @ModelAttribute ProjectDTO projectDTO) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByMail(email);

        Tika tika = new Tika();

        String detectedType = tika.detect(projectDTO.getVideo().getInputStream());

        if (!detectedType.startsWith("video/")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être une vidéo");
        }

        Project project = projectService.getProject(id);
        String newVideo = uploads.updateFile(projectDTO.getVideo(), project.getVideo());
        List<Tool> selectedTools = toolService.getToolsByIds(projectDTO.getTools());

        List<Document> savedDocuments = new ArrayList<>();
        if (projectDTO.getDocuments() != null && projectDTO.getDocuments().size() > 0) {
            for (MultipartFile doc : projectDTO.getDocuments()) {
                String type = tika.detect(doc.getInputStream());
                if (type.startsWith("application/pdf")) {
                    String path = uploads.saveFile(doc);
                    Document document = new Document(doc.getOriginalFilename(), path);
                    documentService.createDocument(document);
                    savedDocuments.add(document);
                }
            }
        }

        project.setDocuments(savedDocuments);
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setVideo(newVideo);
        project.setSourceCode(projectDTO.getSourcecode());
        project.setUser(user);
        project.setUpdateDate(LocalDateTime.now());
        project.setTools(selectedTools);

        ResponseMessage result = this.projectService.updateProject(project);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProjectDTO>> listProjects() {
        List<Project> projects = projectService.getAllProject();
        List<ProjectDTO> projects_dto = projects.stream()
                .map(p -> new ProjectDTO(
                        p.getName(),
                        p.getDescription(),
                        p.getVideo(),
                        p.getSourceCode(),
                        p.getUser().getFirstname() + " " + p.getUser().getLastname(),
                        p.getUser().getTitle(),
                        p.getUser().getPhoto()))
                .toList();
        return ResponseEntity.ok(projects_dto);
    }

    @GetMapping("/details/{id}")
    public Project getProject(@PathVariable Long id) {
        return projectService.getProject(id);
    }

    @GetMapping("/commentbyproject/{id}")
    public List<Comment> getCommentsByProject(@PathVariable Long id) {
        List<Comment> comments = commentService.getCommentsByProject(id);
        return comments;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteProjet(@PathVariable Long id) {

        Project project = getProject(id);
        List<Document> documents = new ArrayList<>(project.getDocuemnts());

        // Détacher les relations ManyToMany
        project.getDocuemnts().clear();
        project.getTools().clear();

        projectService.addProject(project);

        uploads.deleteFile(project.getVideo());

        for (Document doc : documents) {
            uploads.deleteFile(doc.getFile());
            documentService.deleteDocument(doc.getId());
        }

        return projectService.deleteProject(id);

    }

}

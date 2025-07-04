package com.prouvetech.prouvetech.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<ResponseMessage> addProject(@ModelAttribute ProjectDTO projectDTO) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByMail(email);

        Tika tika = new Tika();

        String detectedType = tika.detect(projectDTO.getVideo().getInputStream());

        if (!detectedType.startsWith("video/")) {
            ResponseMessage message = new ResponseMessage(false, "Le fichier n'est pas une vidéo");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);

        }

        // List<Document> savedDocuments = new ArrayList<>();
        // for (MultipartFile doc : projectDTO.getDocuments()) {
        // String type = tika.detect(doc.getInputStream());
        // if (type.startsWith("application/pdf")) {
        // String path = uploads.saveFile(doc);
        // Document document = new Document(doc.getOriginalFilename(), path);
        // documentService.createDocument(document);
        // savedDocuments.add(document);
        // }
        // }

        String newVideo = uploads.saveFile(projectDTO.getVideo());
        List<Tool> selectedTools = toolService.getToolsByIds(projectDTO.getTools());

        Project project = new Project(
                projectDTO.getName(),
                projectDTO.getDescription(),
                newVideo,
                projectDTO.getSourcecode(),
                user,
                selectedTools,
                LocalDateTime.now(),
                LocalDateTime.now());

        ResponseMessage result = projectService.addProject(project);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);

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

        // List<Document> savedDocuments = new ArrayList<>();

        // if (projectDTO.getDocuments() != null && projectDTO.getDocuments().size() >
        // 0) {
        // for (MultipartFile doc : projectDTO.getDocuments()) {
        // String type = tika.detect(doc.getInputStream());
        // if (type.startsWith("application/pdf")) {
        // String path = uploads.saveFile(doc);
        // Document document = new Document(doc.getOriginalFilename(), path);
        // documentService.createDocument(document);
        // savedDocuments.add(document);
        // }
        // }
        // }

        // project.setDocuments(savedDocuments);
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
                        p.getId(),
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
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long id) {

        Project p = projectService.getProject(id);

        List<Long> toolIds = p.getTools().stream()
                .map(Tool::getId)
                .collect(Collectors.toList());
        List<Tool> tools = toolService.getToolsByIds(toolIds);
        List<String> toolNames = tools.stream()
                .map(Tool::getName)
                .collect(Collectors.toList());

        ProjectDTO projectDTO = new ProjectDTO(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getVideo(),
                p.getSourceCode(),
                p.getUser().getFirstname() + " " + p.getUser().getLastname(),
                p.getUser().getTitle(),
                p.getUser().getPhoto(),
                toolNames);

        return ResponseEntity.ok(projectDTO);
    }

    @GetMapping("/commentbyproject/{id}")
    public List<Comment> getCommentsByProject(@PathVariable Long id) {
        List<Comment> comments = commentService.getCommentsByProject(id);
        return comments;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteProjet(@PathVariable Long id) {

        Project project = this.projectService.getProject(id);
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

    @GetMapping("/search")
    public ResponseEntity<List<ProjectDTO>> searchProject(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<Long> toolsIds) {

        List<Project> projects = projectService.searchProjects(name, toolsIds);
        List<ProjectDTO> projects_dto = projects.stream()
                .map(p -> new ProjectDTO(
                        p.getId(),
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

}

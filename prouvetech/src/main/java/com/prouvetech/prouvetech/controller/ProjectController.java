package com.prouvetech.prouvetech.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prouvetech.prouvetech.model.Comment;
import com.prouvetech.prouvetech.model.Document;
import com.prouvetech.prouvetech.model.Project;
import com.prouvetech.prouvetech.model.Tool;
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
    public ResponseEntity<String> addProject(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("sourcecode") String sourcecode,
            @RequestParam("video") MultipartFile video,
            @RequestParam("documents") MultipartFile[] documents,
            @RequestParam("tools") List<Long> tools,
            @RequestParam("userId") Long userId) throws IOException {

        Tika tika = new Tika();

        String detectedType = tika.detect(video.getInputStream());

        if (!detectedType.startsWith("video/")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être une vidéo");
        }

        List<Document> savedDocuments = new ArrayList<>();
        for (MultipartFile doc : documents) {
            String type = tika.detect(doc.getInputStream());
            if (type.startsWith("application/pdf")) {
                String path = uploads.saveFile(doc);
                Document document = new Document(doc.getOriginalFilename(), path);
                documentService.createDocument(document);
                savedDocuments.add(document);
            }
        }

        String newVideo = uploads.saveFile(video);
        List<Tool> selectedTools = toolService.getToolsByIds(tools);

        Project project = new Project(
                name,
                description,
                newVideo,
                sourcecode,
                userService.getUser(userId),
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
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("sourcecode") String sourcecode,
            @RequestParam("video") MultipartFile video,
            @RequestParam("documents") MultipartFile[] documents,
            @RequestParam("tools") List<Long> tools,
            @RequestParam("userId") Long userId) throws IOException {

        Tika tika = new Tika();

        String detectedType = tika.detect(video.getInputStream());

        if (!detectedType.startsWith("video/")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être une vidéo");
        }

        Project project = projectService.getProject(id);
        String newVideo = uploads.updateFile(video, project.getVideo());
        List<Tool> selectedTools = toolService.getToolsByIds(tools);

        List<Document> savedDocuments = new ArrayList<>();
        if (documents != null && documents.length > 0) {
            for (MultipartFile doc : documents) {
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
        project.setName(name);
        project.setDescription(description);
        project.setVideo(newVideo);
        project.setSourceCode(sourcecode);
        project.setUser(userService.getUser(userId));
        project.setUpdateDate(LocalDateTime.now());
        project.setTools(selectedTools);

        ResponseMessage result = this.projectService.updateProject(project);

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

    @GetMapping("/data/{id}")
    public ResponseMessage getData(@PathVariable Long id) {
        return projectService.details(id);
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

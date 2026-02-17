package com.prouvetech.prouvetech.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// import org.apache.tika.Tika;
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
import com.prouvetech.prouvetech.dto.StatusDTO;
import com.prouvetech.prouvetech.dto.ToolDTO;
import com.prouvetech.prouvetech.dto.UserDTO;
import com.prouvetech.prouvetech.model.Project;
import com.prouvetech.prouvetech.model.Tool;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.model.Demo;
import com.prouvetech.prouvetech.service.DemoService;
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
        private DemoService demoService;

        @Autowired
        private ProjectService projectService;

        @Autowired
        private ToolService toolService;

        @Autowired
        private UserService userService;

        @Autowired
        private Uploads uploads;

        @PostMapping("/create")
        public ResponseEntity<ResponseMessage> addProject(@ModelAttribute ProjectDTO projectDTO) throws IOException {

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String email = authentication.getName();
                User user = userService.getUserByMail(email);

                long projectCount = projectService.countProjectsByUser(user);
                if (projectCount >= 3) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                        .body(new ResponseMessage(false,
                                                        "Vous avez atteint la limite de 3 projets maximum."));
                }

                String newThumbnail = uploads.saveFile(projectDTO.getThumbnail());
                List<Tool> selectedTools = toolService.getToolsByIds(projectDTO.getToolsIds());

                Project project = new Project(
                                projectDTO.getName(),
                                projectDTO.getDescription(),
                                newThumbnail,
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
        public ResponseEntity<ResponseMessage> updateProject(
                        @PathVariable Long id,
                        @ModelAttribute ProjectDTO projectDTO) throws IOException {

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String email = authentication.getName();
                User user = userService.getUserByMail(email);
                Project project = projectService.getProject(id);

                if (projectDTO.getThumbnail() != null) {
                        String newThumbnail = uploads.updateFile(projectDTO.getThumbnail(), project.getThumbnail());
                        project.setThumbnail(newThumbnail);
                }

                if (projectDTO.getToolsIds() != null) {
                        List<Tool> selectedTools = toolService.getToolsByIds(projectDTO.getToolsIds());
                        project.setTools(selectedTools);
                }
                project.setName(projectDTO.getName());
                project.setDescription(projectDTO.getDescription());
                project.setSourceCode(projectDTO.getSourcecode());
                project.setUser(user);
                project.setUpdateDate(LocalDateTime.now());

                ResponseMessage result = this.projectService.updateProject(project);

                if (!result.isSuccess()) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
                }

                return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }

        @GetMapping("/list")
        public ResponseEntity<List<ProjectDTO>> listProjects() {
                List<Project> projects = projectService.getAllProject();
                List<ProjectDTO> projects_dto = projects.stream()
                                .map(p -> new ProjectDTO(
                                                p.getId(),
                                                p.getName(),
                                                p.getDescription(),
                                                p.getThumbnail(),
                                                p.getSourceCode(),
                                                new UserDTO(
                                                                p.getUser().getId(),
                                                                p.getUser().getFirstname(),
                                                                p.getUser().getLastname(),
                                                                p.getUser().getMail(),
                                                                p.getUser().getTitle(),
                                                                p.getUser().getDescription(),
                                                                new StatusDTO(
                                                                                p.getUser().getStatus().getId(),
                                                                                p.getUser().getStatus().getName()),
                                                                p.getUser().getPhoto())))
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
                List<ToolDTO> toolsDto = tools.stream()
                                .map(t -> new ToolDTO(t.getId(), t.getName()))
                                .collect(Collectors.toList());

                ProjectDTO projectDTO = new ProjectDTO(
                                p.getId(),
                                p.getName(),
                                p.getDescription(),
                                p.getThumbnail(),
                                p.getSourceCode(),
                                new UserDTO(
                                                p.getUser().getId(),
                                                p.getUser().getFirstname(),
                                                p.getUser().getLastname(),
                                                p.getUser().getMail(),
                                                p.getUser().getTitle(),
                                                p.getUser().getDescription(),
                                                new StatusDTO(
                                                                p.getUser().getStatus().getId(),
                                                                p.getUser().getStatus().getName()),
                                                p.getUser().getPhoto()),
                                toolsDto);

                return ResponseEntity.ok(projectDTO);
        }

        @GetMapping("/myproject/{id}")
        public ResponseEntity<ProjectDTO> getMyProject(@PathVariable Long id) {

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                User user = userService.getUserByMail(authentication.getName());

                Project p = projectService.getMyProject(id, user.getId());

                List<Long> toolIds = p.getTools().stream()
                                .map(Tool::getId)
                                .collect(Collectors.toList());
                List<Tool> tools = toolService.getToolsByIds(toolIds);
                List<ToolDTO> toolsDto = tools.stream()
                                .map(t -> new ToolDTO(t.getId(), t.getName()))
                                .collect(Collectors.toList());

                ProjectDTO projectDTO = new ProjectDTO(
                                p.getId(),
                                p.getName(),
                                p.getDescription(),
                                p.getThumbnail(),
                                p.getSourceCode(),
                                new UserDTO(
                                                p.getUser().getId(),
                                                p.getUser().getFirstname(),
                                                p.getUser().getLastname(),
                                                p.getUser().getMail(),
                                                p.getUser().getTitle(),
                                                p.getUser().getDescription(),
                                                new StatusDTO(
                                                                p.getUser().getStatus().getId(),
                                                                p.getUser().getStatus().getName()),
                                                p.getUser().getPhoto()),
                                toolsDto);

                return ResponseEntity.ok(projectDTO);
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
                                                p.getThumbnail(),
                                                p.getSourceCode(),
                                                new UserDTO(
                                                                p.getUser().getId(),
                                                                p.getUser().getFirstname(),
                                                                p.getUser().getLastname(),
                                                                p.getUser().getMail(),
                                                                p.getUser().getTitle(),
                                                                p.getUser().getDescription(),
                                                                new StatusDTO(
                                                                                p.getUser().getStatus().getId(),
                                                                                p.getUser().getStatus().getName()),
                                                                p.getUser().getPhoto())))
                                .toList();
                return ResponseEntity.ok(projects_dto);

        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<ResponseMessage> deleteProject(@PathVariable Long id) {

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String email = authentication.getName();
                User currentUser = userService.getUserByMail(email);

                Project project = projectService.getProject(id);

                boolean isOwner = project.getUser().getId().equals(currentUser.getId());
                boolean isAdmin = currentUser.getRoles().stream()
                                .anyMatch(r -> "ROLE_ADMIN".equals(r.getName()));

                if (!isOwner && !isAdmin) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                        .body(new ResponseMessage(false,
                                                        "Vous n’êtes pas autorisé à supprimer ce projet."));
                }

                List<Demo> demos = demoService.getDemosByProjectId(project.getId());
                for (Demo demo : demos) {
                        uploads.deleteFile(demo.getThumbnail());
                        uploads.deleteFile(demo.getVideo());
                        demoService.deleteDemo(demo.getId());
                }

                uploads.deleteFile(project.getThumbnail());
                ResponseMessage result = projectService.deleteProject(project.getId());

                if (!result.isSuccess()) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
                }
                return ResponseEntity.ok(result);
        }

}

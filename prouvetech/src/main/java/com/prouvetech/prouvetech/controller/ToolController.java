package com.prouvetech.prouvetech.controller;

import java.io.IOException;
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

import com.prouvetech.prouvetech.model.Tool;
import com.prouvetech.prouvetech.service.ToolService;
import com.prouvetech.prouvetech.utils.ResponseMessage;
import com.prouvetech.prouvetech.utils.Uploads;

@RestController
@RequestMapping("/tool")
public class ToolController {

    public ToolController() {

    }

    @Autowired
    private ToolService toolService;

    @Autowired
    private Uploads uploads;

    @PostMapping("/create")
    public ResponseEntity<String> addTool(
            @RequestParam("name") String name,
            @RequestParam("logo") MultipartFile logo) throws IOException {

        if (toolService.toolExistsByName(name)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le nom existe déjà, choisissez un autre nom.");
        }

        Tika tika = new Tika();

        String detectedType = tika.detect(logo.getInputStream());

        if (!detectedType.startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être une image");
        }

        String logoUrl = uploads.saveFile(logo);
        Tool tool = new Tool(name, logoUrl);

        ResponseMessage result = toolService.createTool(tool);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTool(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("logo") MultipartFile logo) throws IOException {

        if (toolService.toolExistsByName(name)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le nom existe déjà, choisissez un autre nom.");
        }

        Tika tika = new Tika();

        String detectedType = tika.detect(logo.getInputStream());

        if (!detectedType.startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être une image");
        }

        Tool tool = this.getTool(id);
        String logoUrl = uploads.updateFile(logo, tool.getLogo());

        tool.setName(name);
        tool.setLogo(logoUrl);

        ResponseMessage result = this.toolService.updateTool(id, tool);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @GetMapping("/list")
    public List<Tool> listTools() {
        return toolService.getAllTools();
    }

    @GetMapping("/details/{id}")
    public Tool getTool(@PathVariable Long id) {
        return toolService.getTool(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteTool(@PathVariable Long id) {
        Tool tool = getTool(id);
        uploads.deleteFile(tool.getLogo());
        return toolService.deleteTool(id);
    }

}

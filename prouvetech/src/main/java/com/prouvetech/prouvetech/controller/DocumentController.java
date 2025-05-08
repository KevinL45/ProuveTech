package com.prouvetech.prouvetech.controller;

import java.io.IOException;
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

import com.prouvetech.prouvetech.model.Document;
import com.prouvetech.prouvetech.service.DocumentService;
import com.prouvetech.prouvetech.utils.ResponseMessage;
import com.prouvetech.prouvetech.utils.Uploads;

@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private Uploads uploads;

    @PostMapping("/create")
    public ResponseEntity<String> addDocument(
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) throws IOException {

        Tika tika = new Tika();

        String detectedType = tika.detect(file.getInputStream());

        if (!detectedType.startsWith("application/pdf")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être un pdf");
        }

        String newFile = uploads.saveFile(file);
        Document document = new Document(name, newFile);

        ResponseMessage result = documentService.createDocument(document);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDocument(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) throws IOException {

        Tika tika = new Tika();

        String detectedType = tika.detect(file.getInputStream());

        if (!detectedType.startsWith("application/pdf")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être un pdf");
        }

        Document document = this.getDocument(id);
        String newFile = uploads.updateFile(file, document.getFile());
        Document updateDocument = new Document(name, newFile);

        ResponseMessage result = this.documentService.updateDocument(id, updateDocument);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @GetMapping("/list")
    public List<Document> listDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping("/details/{id}")
    public Document getDocument(@PathVariable Long id) {
        return documentService.getDocument(id);
    }

}

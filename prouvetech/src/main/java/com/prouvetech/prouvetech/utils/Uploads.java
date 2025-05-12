package com.prouvetech.prouvetech.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Uploads {

    public String saveFile(MultipartFile file) throws IOException {

        Tika tika = new Tika();

        String detectedType = tika.detect(file.getInputStream());

        String uploadDir = "";

        if (detectedType.startsWith("video/")) {

            // Définir l'emplacement de stockage
            uploadDir = "uploads/videos/";

        } else if (detectedType.startsWith("image/")) {

            // Définir l'emplacement de stockage
            uploadDir = "uploads/logos/";

        } else if (detectedType.startsWith("application/pdf")) {

            // Définir l'emplacement de stockage
            uploadDir = "uploads/documents/";

        }

        File uploadPath = new File(uploadDir);

        // Créer le dossier s'il n'existe pas
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // Sauvegarder l'image avec un nom unique
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retourner l'URL de l'image
        return "/" + uploadDir + fileName;
    }

    public String updateFile(MultipartFile file, String old) throws IOException {
        Tika tika = new Tika();

        String detectedType = tika.detect(file.getInputStream());

        String uploadDir = "";

        if (detectedType.startsWith("video/")) {

            // Définir l'emplacement de stockage
            uploadDir = "uploads/videos/";

        } else if (detectedType.startsWith("image/")) {

            // Définir l'emplacement de stockage
            uploadDir = "uploads/logos/";

        } else if (detectedType.startsWith("application/pdf")) {

            // Définir l'emplacement de stockage
            uploadDir = "uploads/documents/";

        }
        File uploadPath = new File(uploadDir);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // Supprimer l'ancienne image si elle existe
        if (old != null && !old.isEmpty()) {
            Path oldFilePath = Paths.get(old.replace("/" + uploadDir, uploadDir));
            File oldFile = oldFilePath.toFile();
            if (oldFile.exists()) {
                oldFile.delete(); // Suppression du fichier
            }
        }

        // Sauvegarder la nouvelle image avec un nom unique
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retourner la nouvelle URL de l'image
        return "/" + uploadDir + fileName;
    }

    public void deleteFile(String linkFile) {

        // Supprimer l'ancienne image
        Path oldFilePath = Paths.get("/" + linkFile);
        File oldFile = oldFilePath.toFile();
        if (oldFile.exists()) {
            oldFile.delete();
        }

    }

}

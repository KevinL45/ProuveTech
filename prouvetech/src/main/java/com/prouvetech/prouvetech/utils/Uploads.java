package com.prouvetech.prouvetech.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Uploads {

    public String saveFile(MultipartFile file) throws IOException {

        // Définir l'emplacement de stockage
        String uploadDir = "uploads/";
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
        return "/uploads/" + fileName;
    }

    public String updateFile(MultipartFile file, String oldImageUrl) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide ou invalide.");
        }

        String uploadDir = "uploads/";
        File uploadPath = new File(uploadDir);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // Supprimer l'ancienne image si elle existe
        if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
            Path oldFilePath = Paths.get(oldImageUrl.replace("/uploads/", uploadDir));
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
        return "/uploads/" + fileName;
    }

}

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
import org.springframework.web.multipart.MultipartFile;

import com.prouvetech.prouvetech.model.SocialNetworks;
import com.prouvetech.prouvetech.service.SocialNetworksService;
import com.prouvetech.prouvetech.utils.ResponseMessage;
import com.prouvetech.prouvetech.utils.Uploads;

@RequestMapping("/social_networks")
public class SocialNetworksController {

    public SocialNetworksController() {

    }

    @Autowired
    private SocialNetworksService socialNetworksService;

    @Autowired
    private Uploads uploads;

    @PostMapping("/create")
    public ResponseEntity<String> addSocialNetworks(
            @RequestParam("name") String name,
            @RequestParam("logo") MultipartFile logo) throws IOException {

        if (socialNetworksService.SNExistsByName(name)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le nom existe déjà, choisissez un autre nom.");
        }

        Tika tika = new Tika();

        String detectedType = tika.detect(logo.getInputStream());

        if (!detectedType.startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être une image");
        }

        String logoUrl = uploads.saveFile(logo);
        SocialNetworks socialNetworks = new SocialNetworks(name, logoUrl);

        ResponseMessage result = socialNetworksService.createSocialNetworks(socialNetworks);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSocialNetworks(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("logo") MultipartFile logo) throws IOException {

        if (socialNetworksService.SNExistsByName(name)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le nom existe déjà, choisissez un autre nom.");
        }

        Tika tika = new Tika();

        String detectedType = tika.detect(logo.getInputStream());

        if (!detectedType.startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être une image");
        }

        SocialNetworks socialNetworks = this.socialNetworksService.getSocialNetworks(id);
        String logoUrl = uploads.updateFile(logo, socialNetworks.getLogoUrl());
        SocialNetworks socialNetworksUpdate = new SocialNetworks(name, logoUrl);

        ResponseMessage result = this.socialNetworksService.updateSocialNetworks(id, socialNetworksUpdate);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @GetMapping("/list")
    public List<SocialNetworks> listSocialNetworks() {
        return socialNetworksService.getAllSocialNetworks();
    }

    @GetMapping("/details/{id}")
    public SocialNetworks getSocialNetworks(@PathVariable Long id) {
        return socialNetworksService.getSocialNetworks(id);
    }

}

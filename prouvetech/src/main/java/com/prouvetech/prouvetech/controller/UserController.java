package com.prouvetech.prouvetech.controller;

import com.prouvetech.prouvetech.compositekey.UserSocialNetworksId;
import com.prouvetech.prouvetech.model.Role;
import com.prouvetech.prouvetech.model.SocialNetworks;
import com.prouvetech.prouvetech.model.Status;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.model.UserSocialNetworks;
import com.prouvetech.prouvetech.service.RoleService;
import com.prouvetech.prouvetech.service.SocialNetworksService;
import com.prouvetech.prouvetech.service.StatusService;
import com.prouvetech.prouvetech.service.UserService;
import com.prouvetech.prouvetech.utils.ResponseMessage;
import com.prouvetech.prouvetech.utils.Uploads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    public UserController() {
    }

    @Autowired
    private UserService userService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private SocialNetworksService socialNetworksService;

    @Autowired
    private Uploads uploads;

    @Autowired
    private RoleService roleService;

    @PostMapping("/register")
    public ResponseEntity<String> addUser(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("mail") String mail,
            @RequestParam("password") String password,
            @RequestParam("statusId") Long statusId) {

        Status status = this.statusService.getStatus(statusId);

        User user = new User(firstname, lastname, mail, password, status);
        ResponseMessage result = userService.createUser(user);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());
    }

    @PutMapping("update/")
    public ResponseEntity<String> updateUser(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("mail") String mail,
            @RequestParam("password") String password,
            @RequestParam("statusId") Long statusId,
            @RequestParam("telephone") String telephone,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("link_social_network") List<String> link_social_networks) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.getUserByMail(email);

        Status status = statusService.getStatus(statusId);
        Optional<Role> role = roleService.getRoleByStatus(status);

        Tika tika = new Tika();

        String detectedTypePhoto = tika.detect(photo.getInputStream());

        if (!detectedTypePhoto.startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Le fichier doit être une image");
        }

        String newphoto = uploads.saveFile(photo);

        List<UserSocialNetworks> newUserSocials = new ArrayList<>();

        for (String link : link_social_networks) {
            SocialNetworks socialNetwork = socialNetworksService.detectNetworkNameFromUrl(link);

            if (socialNetwork != null) {
                UserSocialNetworks usn = new UserSocialNetworks();

                UserSocialNetworksId compositeKey = new UserSocialNetworksId(user.getId(), socialNetwork.getId());
                usn.setUserSocialNetworksId(compositeKey);

                usn.setUser(user);
                usn.setSocialNetwork(socialNetwork);
                usn.setProfileUrl(link);
                newUserSocials.add(usn);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Le réseau social pour le lien '" + link + "' n'est pas reconnu.");
            }
        }

        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setMail(mail);
        user.setPassword(password);
        user.setTitle(title);
        user.setDescription(description);
        user.setTelephone(telephone);
        user.setPhoto(newphoto);
        user.setStatus(status);
        user.setRoles(new ArrayList<>(List.of(role.get())));

        user.getSocialNetworks().clear();
        user.getSocialNetworks().addAll(newUserSocials);
        ResponseMessage result = userService.updateUser(user);

        if (!result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getMessage());

    }

    @GetMapping("/details/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/list")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Validated @RequestParam("mail") String mail,
            @RequestParam("password") String password) {

        User user = new User(mail, password);
        ResponseMessage token = userService.authenticateUser(user.getMail(), user.getPassword());

        if (!token.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(token.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(token.getMessage());
    }

}

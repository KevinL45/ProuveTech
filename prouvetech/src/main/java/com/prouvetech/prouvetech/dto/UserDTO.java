package com.prouvetech.prouvetech.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class UserDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String mail;
    private String password;
    private Long statusId;
    private String telephone;
    private String title;
    private MultipartFile photo;
    private String linkPhoto;
    private String description;
    private String statusName;
    private List<String> linksSocialNetworks;

    public UserDTO() {

    }

    // Détails du profil
    public UserDTO(Long id, String firstname, String lastname, String mail, String title, String description,
            String statusName, String linkPhoto) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.title = title;
        this.statusName = statusName;
        this.description = description;
        this.linkPhoto = linkPhoto;
    }

    // Détails du profil avec réseaux soicaux
    public UserDTO(String firstname, String lastname, String mail, String title, String description,
            long statusId, String telephone, List<String> linksSocialNetworks) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.title = title;
        this.statusId = statusId;
        this.description = description;
        this.telephone = telephone;
        this.linksSocialNetworks = linksSocialNetworks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<String> getLinksSocialNetworks() {
        return linksSocialNetworks;
    }

    public void setlinksSocialNnetworks(List<String> linksSocialNetworks) {
        this.linksSocialNetworks = linksSocialNetworks;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public String getLinkPhoto() {
        return linkPhoto;
    }

    public void setLinkPhoto(String linkPhoto) {
        this.linkPhoto = linkPhoto;
    }
}

package com.prouvetech.prouvetech.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class UserDTO {
    private String firstname;
    private String lastname;
    private String mail;
    private String password;
    private Long statusId;
    private String telephone;
    private String title;
    private MultipartFile photo;
    private String link_photo;
    private String description;
    private String statusName;
    private List<String> link_social_networks;

    public UserDTO() {

    }

    // Détails du profil
    public UserDTO(String firstname, String lastname, String mail, String title, String description,
            String statusName, String link_photo) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.title = title;
        this.statusName = statusName;
        this.description = description;
        this.link_photo = link_photo;
    }

    // Détails du profil avec réseaux soicaux
    public UserDTO(String firstname, String lastname, String mail, String title, String description,
            long statusId, String telephone, List<String> link_social_networks) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.title = title;
        this.statusId = statusId;
        this.description = description;
        this.telephone = telephone;
        this.link_social_networks = link_social_networks;
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

    public List<String> getLink_social_networks() {
        return link_social_networks;
    }

    public void setlink_social_networks(List<String> link_social_networks) {
        this.link_social_networks = link_social_networks;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public String getLinkPhoto() {
        return link_photo;
    }

    public void setLinkPhoto(String link_photo) {
        this.link_photo = link_photo;
    }
}

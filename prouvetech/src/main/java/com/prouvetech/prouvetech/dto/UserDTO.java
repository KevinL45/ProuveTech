package com.prouvetech.prouvetech.dto;

public class UserDTO {
    private String mail;
    private String password;
    private String firstname;
    private String lastname;
    private String title;
    private String description;
    private Long statusId;
    private String statusName;

    public UserDTO() {

    }

    public UserDTO(String firstname, String lastname, String mail, Long statusId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.statusId = statusId;
    }

    public UserDTO(String firstname, String lastname, String mail, String title, Long statusId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.title = title;
        this.statusId = statusId;
    }

    public UserDTO(String firstname, String lastname, String mail, String title, String statusName) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.title = title;
        this.statusName = statusName;
    }

    public UserDTO(String firstname, String lastname, String mail, String title, String description,
            String statusName) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.title = title;
        this.statusName = statusName;
        this.description = description;
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
}

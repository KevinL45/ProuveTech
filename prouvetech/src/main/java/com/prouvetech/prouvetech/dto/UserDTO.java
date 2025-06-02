package com.prouvetech.prouvetech.dto;

public class UserDTO {
    private String mail;
    private String password;
    private String firstname;
    private String lastname;
    private String title;
    private Long statusId;

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
}

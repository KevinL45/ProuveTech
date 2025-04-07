package com.prouvetech.prouvetech.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String firstname;

    @Column(nullable = false, length = 100)
    private String lastname;

    @Column(unique = true, nullable = false, length = 100)
    private String mail;

    @JsonIgnore
    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 50)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(length = 10)
    private String telephone;

    private String cv;

    private String photo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSocialNetwork> socialNetworks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public User() {
    }

    public User(String firstname, String lastname, String mail, String password,
            String title, String description, String telephone, String cv, String photo) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
        this.password = password;
        this.title = title;
        this.description = description;
        this.telephone = telephone;
        this.cv = cv;
        this.photo = photo;
    }

    public Long getId() {
        return id;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", mail='" + mail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", telephone='" + telephone + '\'' +
                ", cv='" + cv + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}

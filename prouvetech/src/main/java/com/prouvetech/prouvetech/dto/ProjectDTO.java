package com.prouvetech.prouvetech.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ProjectDTO {
    private String name;

    private String description;
    private String sourcecode;
    private List<Long> tools;

    private MultipartFile video;
    private List<MultipartFile> documents;

    private String fullname;
    private String titleActor;
    private String photoProfile;
    private String link_video;

    private List<String> toolsName;

    public ProjectDTO() {

    }

    public ProjectDTO(String name, String description, String soucecode, List<Long> tools) {
        this.name = name;
        this.description = description;
        this.sourcecode = soucecode;
        this.tools = tools;
    }

    public ProjectDTO(String name, String description, String link_video, String soucecode, String fullname,
            String titleActor, String photoProfile) {
        this.name = name;
        this.description = description;
        this.sourcecode = soucecode;
        this.fullname = fullname;
        this.titleActor = titleActor;
        this.photoProfile = photoProfile;
        this.link_video = link_video;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourcecode() {
        return sourcecode;
    }

    public void setSourcecode(String sourcecode) {
        this.sourcecode = sourcecode;
    }

    public List<Long> getTools() {
        return tools;
    }

    public void setTools(List<Long> tools) {
        this.tools = tools;
    }

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }

    public List<MultipartFile> getDocuments() {
        return documents;
    }

    public void setDocuments(List<MultipartFile> documents) {
        this.documents = documents;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTitleActor() {
        return titleActor;
    }

    public void setTitleActor(String titleActor) {
        this.titleActor = titleActor;
    }

    public String getPhotoProfile() {
        return photoProfile;
    }

    public void setPhotoProfile(String photoProfile) {
        this.photoProfile = photoProfile;
    }

    public List<String> getToolsName() {
        return toolsName;
    }

    public void setToolsName(List<String> toolsName) {
        this.toolsName = toolsName;
    }

    public String getLink_video() {
        return link_video;
    }

    public void setLink_video(String link_video) {
        this.link_video = link_video;
    }

}

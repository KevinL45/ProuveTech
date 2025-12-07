package com.prouvetech.prouvetech.dto;

import org.springframework.web.multipart.MultipartFile;

public class DemoDTO {

    private long id;
    private String name;
    private String description;
    private MultipartFile thumbnail;
    private String link_thumbnail;
    private MultipartFile video;
    private String link_video;
    private ProjectDTO projectDTO;

    public DemoDTO() {

    }

    public DemoDTO(long id, String name, String description,
            String link_thumbnail, String link_video,
            ProjectDTO projectDTO) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.link_thumbnail = link_thumbnail;
        this.link_video = link_video;
        this.projectDTO = projectDTO;
    }

    public DemoDTO(String name, String description,
            String link_thumbnail, String link_video,
            ProjectDTO projectDTO) {

        this.name = name;
        this.description = description;
        this.link_thumbnail = link_thumbnail;
        this.link_video = link_video;
        this.projectDTO = projectDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public MultipartFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MultipartFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLink_thumbnail() {
        return link_thumbnail;
    }

    public void setLink_Thumbnail(String link_thumbnail) {
        this.link_thumbnail = link_thumbnail;
    }

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }

    public String getLink_video() {
        return link_video;
    }

    public void setLink_vdieo(String link_video) {
        this.link_video = link_video;
    }

    public ProjectDTO getProjectDTO() {
        return projectDTO;
    }

    public void setProjectDTO(ProjectDTO projectDTO) {
        this.projectDTO = projectDTO;
    }

}

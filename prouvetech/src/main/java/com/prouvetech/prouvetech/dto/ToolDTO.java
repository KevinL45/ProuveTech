package com.prouvetech.prouvetech.dto;

public class ToolDTO {

    private long id;
    private String name;
    private String logo;

    public ToolDTO() {

    }

    public ToolDTO(String name, String logo) {
        this.name = name;
        this.logo = logo;

    }

    public ToolDTO(Long id, String name, String logo) {
        this.name = name;
        this.logo = logo;
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setLong(Long id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}

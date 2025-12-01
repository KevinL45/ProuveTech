package com.prouvetech.prouvetech.dto;

public class ToolDTO {

    private long id;
    private String name;

    public ToolDTO() {

    }

    public ToolDTO(String name) {
        this.name = name;
    }

    public ToolDTO(long id, String name) {
        this.name = name;
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

}

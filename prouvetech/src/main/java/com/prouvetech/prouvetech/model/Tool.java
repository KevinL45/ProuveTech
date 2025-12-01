package com.prouvetech.prouvetech.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tools", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Tool implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public Tool() {
    }

    public Tool(String name) {
        this.name = name;
    }

    // Relation Many-to-Many avec Project
    @ManyToMany(mappedBy = "tools")
    private List<Project> projects = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    // === toString ===

    @Override
    public String toString() {
        return "Tool{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

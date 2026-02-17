package com.prouvetech.prouvetech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prouvetech.prouvetech.model.Demo;
import com.prouvetech.prouvetech.model.Project;
import com.prouvetech.prouvetech.model.User;

public interface DemoRepository extends JpaRepository<Demo, Long> {

    List<Demo> findByNameContainingIgnoreCase(String name);

    List<Demo> findByProjectId(Long projectId);

    long countByProjectUser(User user);

    long countByProject(Project project);
}

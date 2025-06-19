package com.prouvetech.prouvetech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prouvetech.prouvetech.model.Project;
import com.prouvetech.prouvetech.model.User;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUser(User user);

}

package com.prouvetech.prouvetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prouvetech.prouvetech.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}

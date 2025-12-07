package com.prouvetech.prouvetech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prouvetech.prouvetech.model.Demo;

public interface DemoRepository extends JpaRepository<Demo, Long> {

    List<Demo> findByNameContainingIgnoreCase(String name);
}

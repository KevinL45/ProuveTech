package com.prouvetech.prouvetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prouvetech.prouvetech.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

}

package com.prouvetech.prouvetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prouvetech.prouvetech.compositekey.CommentId;
import com.prouvetech.prouvetech.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, CommentId> {

}

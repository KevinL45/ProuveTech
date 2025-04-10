package com.prouvetech.prouvetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prouvetech.prouvetech.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByMail(String mail);

}

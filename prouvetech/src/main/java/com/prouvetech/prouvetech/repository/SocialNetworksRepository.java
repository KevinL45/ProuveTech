package com.prouvetech.prouvetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prouvetech.prouvetech.model.SocialNetworks;

@Repository
public interface SocialNetworksRepository extends JpaRepository<SocialNetworks, Long> {

}

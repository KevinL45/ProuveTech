package com.prouvetech.prouvetech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prouvetech.prouvetech.compositekey.UserSocialNetworksId;
import com.prouvetech.prouvetech.model.UserSocialNetworks;

@Repository
public interface UserSocialNetworksRepository extends JpaRepository<UserSocialNetworks, UserSocialNetworksId> {
    List<UserSocialNetworks> findByUserId(Long userId);

}

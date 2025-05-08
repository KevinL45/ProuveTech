package com.prouvetech.prouvetech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.UserSocialNetworks;
import com.prouvetech.prouvetech.repository.UserSocialNetworksRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;

@Service
public class UserSocialNetworksService {

    @Autowired
    private UserSocialNetworksRepository userSocialNetworksRepository;

    public UserSocialNetworks getUserSocialNetworks(long id) {
        return userSocialNetworksRepository.findById(null)
                .orElseThrow(() -> new RuntimeException("Aucun réseau sociaux trouvé avec l'ID : " + id));
    }

    public List<UserSocialNetworks> getAllUserSocialNetworks() {
        return userSocialNetworksRepository.findAll();
    }

    public ResponseMessage addUserSocialNetworks(UserSocialNetworks userSocialNetworks) {
        userSocialNetworksRepository.save(userSocialNetworks);
        return new ResponseMessage(true, userSocialNetworks.getSocialNetwork().getName() + " a été ajouté");
    }

    public ResponseMessage updateUserSocialNetworks(UserSocialNetworks userSocialNetworks, Long id) {
        UserSocialNetworks userSocialNetworksExist = getUserSocialNetworks(id);

        userSocialNetworksExist.setProfileUrl(userSocialNetworks.getProfileUrl());
        userSocialNetworksExist.setSocialNetwork(userSocialNetworks.getSocialNetwork());
        userSocialNetworksExist.setUser(userSocialNetworks.getUser());

        userSocialNetworksRepository.save(userSocialNetworks);

        return new ResponseMessage(true, userSocialNetworks.getSocialNetwork().getName() + " a été modifié");

    }

    // public ResponseMessage deleteComment(Long id) {

    // if (!commentRepository.existsById(id)) {
    // return new ResponseMessage(false, "Aucun de tes commentaires dans ce
    // projet");
    // }
    // commentRepository.deleteById(id);
    // return new ResponseMessage(true, "Le commentaire a été supprimé avec
    // succès.");
    // }

}

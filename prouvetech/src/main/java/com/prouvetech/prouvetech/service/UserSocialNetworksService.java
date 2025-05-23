package com.prouvetech.prouvetech.service;

import com.prouvetech.prouvetech.compositekey.UserSocialNetworksId;
import com.prouvetech.prouvetech.model.SocialNetworks;
import com.prouvetech.prouvetech.model.User;
import com.prouvetech.prouvetech.model.UserSocialNetworks;
import com.prouvetech.prouvetech.repository.UserSocialNetworksRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSocialNetworksService {

    @Autowired
    private UserSocialNetworksRepository usnRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SocialNetworksService socialNetworksService;

    public ResponseMessage addSocialNetwork(Long userId, Long socialNetworkId, String profileUrl) {
        User user = userService.getUser(userId);
        SocialNetworks socialNetwork = socialNetworksService.getSocialNetworks(socialNetworkId);

        UserSocialNetworksId id = new UserSocialNetworksId(userId, socialNetworkId);
        if (usnRepository.existsById(id)) {
            return new ResponseMessage(false, "Ce réseau social est déjà lié à l'utilisateur.");
        }

        UserSocialNetworks usn = new UserSocialNetworks(user, socialNetwork, profileUrl);
        // usn.setUserSocialNetworksId(id);
        usnRepository.save(usn);
        return new ResponseMessage(true, "Réseau social ajouté.");
    }

    public ResponseMessage updateProfileUrl(Long userId, Long socialNetworkId, String profileUrl) {
        UserSocialNetworksId id = new UserSocialNetworksId(userId, socialNetworkId);
        Optional<UserSocialNetworks> optional = usnRepository.findById(id);

        if (optional.isEmpty()) {
            return new ResponseMessage(false, "Lien inexistant.");
        }

        UserSocialNetworks usn = optional.get();
        usn.setProfileUrl(profileUrl);
        usnRepository.save(usn);
        return new ResponseMessage(true, "Profil mis à jour.");
    }

    public ResponseMessage deleteSocialNetwork(Long userId, Long socialNetworkId) {
        UserSocialNetworksId id = new UserSocialNetworksId(userId, socialNetworkId);
        if (!usnRepository.existsById(id)) {
            return new ResponseMessage(false, "Lien introuvable.");
        }

        usnRepository.deleteById(id);
        return new ResponseMessage(true, "Réseau supprimé.");
    }

    public List<UserSocialNetworks> getUserSocialNetworks(Long userId) {
        return usnRepository.findByUserId(userId);
    }

}

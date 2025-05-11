package com.prouvetech.prouvetech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.SocialNetworks;
import com.prouvetech.prouvetech.repository.SocialNetworksRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;

@Service
public class SocialNetworksService {

    @Autowired
    private SocialNetworksRepository socialNetworksRepository;

    public SocialNetworks getSocialNetworks(Long id) {
        return socialNetworksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun réseau sociaux trouvé avec l'ID : " + id));
    }

    public ResponseMessage createSocialNetworks(SocialNetworks socialNetworks) {

        socialNetworksRepository.save(socialNetworks);
        return new ResponseMessage(true, socialNetworks.getName() + " ajouté avec succès.");
    }

    public ResponseMessage updateSocialNetworks(SocialNetworks socialNetworks) {

        socialNetworksRepository.save(socialNetworks);
        return new ResponseMessage(true, socialNetworks.getName() + " a été mise à jour avec succès.");
    }

    public ResponseMessage deleteSocialNetworks(Long id) {
        if (!socialNetworksRepository.existsById(id)) {
            return new ResponseMessage(false, "Aucun réseaux sociaux trouvé avec l'ID : " + id);
        }
        socialNetworksRepository.deleteById(id);
        return new ResponseMessage(true, "le réseau a été supprimé.");
    }

    public boolean SNExistsByName(String name) {
        return socialNetworksRepository.findByName(name) != null;
    }

    public List<SocialNetworks> getAllSocialNetworks() {
        return socialNetworksRepository.findAll();
    }

}

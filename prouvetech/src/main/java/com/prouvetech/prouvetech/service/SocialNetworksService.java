package com.prouvetech.prouvetech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.SocialNetworks;
import com.prouvetech.prouvetech.repository.SocialNetworksRepository;

@Service
public class SocialNetworksService {

    @Autowired
    private SocialNetworksRepository socialNetworksRepository;

    public SocialNetworks getSocialNetworks(Long id) {
        return socialNetworksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun réseau sociaux trouvé avec l'ID : " + id));
    }

    public String createSocialNetworks(SocialNetworks socialNetworks) {
        if (socialNetworksRepository.findByName(socialNetworks.getName()) != null) {
            return "Le nom existe déjà, choisissez un autre nom.";
        }
        socialNetworksRepository.save(socialNetworks);
        return socialNetworks.getName() + " a été bien ajouté.";
    }

    public String updateSocialNetworks(Long id, SocialNetworks socialNetworks) {
        SocialNetworks socialNetworksExist = getSocialNetworks(id);

        if (socialNetworksRepository.findByName(socialNetworks.getName()) != null &&
                !socialNetworks.getName().equalsIgnoreCase(socialNetworks.getName())) {
            return "Le nom existe déjà, choisissez un autre nom.";
        }

        socialNetworksExist.setName(socialNetworks.getName());
        socialNetworksExist.setLogoUrl(socialNetworks.getLogoUrl());
        socialNetworksRepository.save(socialNetworks);
        return socialNetworks.getName() + " a été mis à jour avec succès.";
    }

    public String deleteSocialNetworks(Long id) {
        if (!socialNetworksRepository.existsById(id)) {
            return "Aucun réseaux sociaux trouvé avec l'ID : " + id;
        }
        socialNetworksRepository.deleteById(id);
        return "Le réseau sociaux a été supprimé avec succès.";
    }

}

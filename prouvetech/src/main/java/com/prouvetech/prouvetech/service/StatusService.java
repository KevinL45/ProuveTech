package com.prouvetech.prouvetech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prouvetech.prouvetech.model.Status;
import com.prouvetech.prouvetech.repository.StatusRepository;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public Status getStatus(Long id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun statut trouvé avec l'ID : " + id));
    }

    public String createStatus(Status status) {
        if (statusRepository.findByName(status.getName()) != null) {
            return "Le nom existe déjà, choisissez un autre nom.";
        }
        statusRepository.save(status);
        return "Le statut a été bien ajouté.";
    }

    public String updateStatus(Long id, Status status) {
        Status statusExist = getStatus(id);

        if (statusRepository.findByName(status.getName()) != null &&
                !statusExist.getName().equalsIgnoreCase(status.getName())) {
            return "Le nom existe déjà, choisissez un autre nom.";
        }

        statusExist.setName(status.getName());
        statusRepository.save(statusExist);
        return "Le statut a été mis à jour avec succès.";
    }

    public String deleteStatus(Long id) {
        if (!statusRepository.existsById(id)) {
            return "Aucun statut trouvé avec l'ID : " + id;
        }
        statusRepository.deleteById(id);
        return "Le statut a été supprimé avec succès.";
    }
}

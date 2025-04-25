package com.prouvetech.prouvetech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.Tool;
import com.prouvetech.prouvetech.repository.ToolRepository;

@Service
public class ToolService {

    @Autowired
    private ToolRepository toolRepository;

    public Tool getTool(Long id) {
        return toolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun outil trouvé avec l'ID : " + id));
    }

    public String createTool(Tool tool) {
        if (toolRepository.findByName(tool.getName()) != null) {
            return "Le nom existe déjà, choisissez un autre nom.";
        }
        toolRepository.save(tool);
        return tool.getName() + " a été bien ajouté.";
    }

    public String updateTool(Long id, Tool tool) {
        Tool toolExist = getTool(id);

        if (toolRepository.findByName(tool.getName()) != null &&
                !tool.getName().equalsIgnoreCase(tool.getName())) {
            return "Le nom existe déjà, choisissez un autre nom.";
        }

        toolExist.setName(tool.getName());
        toolExist.setLogo(tool.getLogo());
        toolRepository.save(tool);
        return tool.getName() + " a été mis à jour avec succès.";
    }

    public String deleteTool(Long id) {
        if (!toolRepository.existsById(id)) {
            return "Aucun outil trouvé avec l'ID : " + id;
        }
        toolRepository.deleteById(id);
        return "L'outil a été supprimé avec succès.";
    }

}

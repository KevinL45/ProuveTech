package com.prouvetech.prouvetech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.Document;
import com.prouvetech.prouvetech.repository.DocumentRepository;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public Document getDocument(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun document trouvé avec l'ID : " + id));
    }

    public String createDocument(Document document) {
        documentRepository.save(document);
        return document.getName() + " a été bien ajouté.";
    }

    public String updateDocument(Long id, Document document) {
        Document documentExist = getDocument(id);

        documentExist.setName(document.getName());
        documentExist.setFile(document.getFile());
        documentRepository.save(document);
        return document.getName() + " a été mis à jour avec succès.";
    }

    public String deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            return "Aucun document trouvé avec l'ID : " + id;
        }
        documentRepository.deleteById(id);
        return "Le document a été supprimé avec succès.";
    }

}

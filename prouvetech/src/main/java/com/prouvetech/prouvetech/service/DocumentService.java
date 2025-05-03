package com.prouvetech.prouvetech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.Document;
import com.prouvetech.prouvetech.repository.DocumentRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public Document getDocument(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun document trouvé avec l'ID : " + id));
    }

    public ResponseMessage createDocument(Document document) {
        documentRepository.save(document);
        return new ResponseMessage(true, document.getName() + " ajouté avec succès.");
    }

    public ResponseMessage updateDocument(Long id, Document document) {
        Document documentExist = getDocument(id);

        documentExist.setName(document.getName());
        documentExist.setFile(document.getFile());
        documentRepository.save(document);
        return new ResponseMessage(true, document.getName() + " ajouté avec succès.");
    }

    public ResponseMessage deleteDocument(Long id) {
        if (!documentRepository.existsById(id)) {
            return new ResponseMessage(false, "Aucun document trouvé avec l'ID : " + id);
        }
        documentRepository.deleteById(id);
        return new ResponseMessage(true, "Le document a été avec succès.");
    }

}

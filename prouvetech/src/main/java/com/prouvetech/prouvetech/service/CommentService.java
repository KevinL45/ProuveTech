package com.prouvetech.prouvetech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prouvetech.prouvetech.model.Comment;
import com.prouvetech.prouvetech.repository.CommentRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment getComment(long id) {
        return commentRepository.findById(null)
                .orElseThrow(() -> new RuntimeException("Aucun commentaire trouvé avec l'ID : " + id));
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public ResponseMessage addComment(Comment comment) {
        commentRepository.save(comment);
        return new ResponseMessage(true, "Le commentaire a été ajouté");
    }

    public ResponseMessage updateComment(Comment comment, Long id) {
        Comment commentExsist = getComment(id);

        commentExsist.setUpdateDate(comment.getUpdateDate());
        commentExsist.setProject(comment.getProject());
        commentExsist.setUser(comment.getUser());
        commentExsist.setContent(comment.getContent());

        commentRepository.save(comment);

        return new ResponseMessage(true, "Le commentaire a été modifié");

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

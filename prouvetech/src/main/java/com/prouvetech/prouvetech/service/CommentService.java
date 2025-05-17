package com.prouvetech.prouvetech.service;

import com.prouvetech.prouvetech.model.*;
import com.prouvetech.prouvetech.compositekey.CommentId;
import com.prouvetech.prouvetech.repository.CommentRepository;
import com.prouvetech.prouvetech.utils.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    public ResponseMessage addComment(Long userId, Long projectId, String content) {
        User user = userService.getUser(userId);
        Project project = projectService.getProject(projectId);

        CommentId commentId = new CommentId(userId, projectId);

        if (commentRepository.existsById(commentId)) {
            return new ResponseMessage(false, "Le commentaire existe déjà pour cet utilisateur et ce projet.");
        }

        Comment comment = new Comment(user, project, content);
        comment.setCommentId(commentId);
        comment.setUpdateDate(LocalDateTime.now());

        commentRepository.save(comment);
        return new ResponseMessage(true, "Commentaire ajouté.");
    }

    public Comment getComment(Long userId, Long projectId) {
        CommentId id = new CommentId(userId, projectId);
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé."));
    }

    public ResponseMessage updateComment(Long userId, Long projectId, String newContent) {
        Comment comment = getComment(userId, projectId);
        comment.setContent(newContent);
        comment.setUpdateDate(LocalDateTime.now());
        commentRepository.save(comment);
        return new ResponseMessage(true, "Commentaire mis à jour.");
    }

    public ResponseMessage deleteComment(Long userId, Long projectId) {
        CommentId id = new CommentId(userId, projectId);
        if (!commentRepository.existsById(id)) {
            return new ResponseMessage(false, "Commentaire introuvable.");
        }
        commentRepository.deleteById(id);
        return new ResponseMessage(true, "Commentaire supprimé.");
    }

    public List<Comment> getCommentsByProject(Long projectId) {
        return commentRepository.findByProjectId(projectId);
    }
}

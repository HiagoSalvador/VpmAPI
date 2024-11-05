package vpmLimp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vpmLimp.DTO.CommentResponse;
import vpmLimp.model.CommentModel;
import vpmLimp.model.UserModel;
import vpmLimp.repositories.CommentModelRepository;
import vpmLimp.validations.CommentValidation;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentModelRepository commentModelRepository;
    private final CommentValidation commentValidation;

    public CommentModel createComment(String commentText, UserModel user) {
        commentValidation.validate(commentText);
        CommentModel comment = new CommentModel();
        comment.setCommentText(commentText);
        comment.setUser(user);
        return commentModelRepository.save(comment);
    }

    public List<CommentResponse> getCommentsByUserEmail(String userEmail) {
        return commentModelRepository.findAll().stream()
                .filter(comment -> comment.getUser().getEmail().equals(userEmail))
                .map(comment -> new CommentResponse(comment.getId(), comment.getCommentText()))
                .collect(Collectors.toList());
    }

    public CommentModel updateComment(Long id, String newCommentText, UserModel user) {
        commentValidation.validate(newCommentText);
        CommentModel existingComment = commentModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentValidation.validateOwnership(existingComment, user);

        existingComment.setCommentText(newCommentText);
        return commentModelRepository.save(existingComment);
    }

    public void deleteComment(Long id, UserModel user) {
        CommentModel existingComment = commentModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentValidation.validateOwnership(existingComment, user);

        commentModelRepository.deleteById(id);
    }

    public CommentModel getCommentById(Long id) {
        return commentModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public void validateOwnership(CommentModel comment, UserModel user) {
        commentValidation.validateOwnership(comment, user);
    }
}

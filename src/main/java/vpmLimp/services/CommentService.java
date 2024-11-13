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

    public CommentResponse createComment(String commentText, UserModel user) {
        commentValidation.validate(commentText);
        CommentModel comment = new CommentModel();
        comment.setCommentText(commentText);
        comment.setUser(user);
        CommentModel savedComment = commentModelRepository.save(comment);
        return new CommentResponse(savedComment.getId(), savedComment.getCommentText());
    }

    public List<CommentResponse> getCommentsByUser(UserModel user) {
        return commentModelRepository.findAll().stream()
                .filter(comment -> comment.getUser().getEmail().equals(user.getEmail()))
                .map(comment -> new CommentResponse(comment.getId(), comment.getCommentText()))
                .collect(Collectors.toList());
    }

    public CommentResponse updateComment(Long id, String newCommentText, UserModel user) {
        validateOwnershipById(id, user);
        commentValidation.validate(newCommentText);

        CommentModel existingComment = getCommentById(id);
        existingComment.setCommentText(newCommentText);

        CommentModel updatedComment = commentModelRepository.save(existingComment);
        return new CommentResponse(updatedComment.getId(), updatedComment.getCommentText());
    }

    public void deleteComment(Long id, UserModel user) {
        validateOwnershipById(id, user);
        commentModelRepository.deleteById(id);
    }

    public CommentModel getCommentById(Long id) {
        return commentModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public void validateOwnershipById(Long id, UserModel user) {
        CommentModel comment = getCommentById(id);
        commentValidation.validateOwnership(comment, user);
    }
}

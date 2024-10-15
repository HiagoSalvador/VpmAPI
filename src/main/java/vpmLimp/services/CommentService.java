package vpmLimp.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vpmLimp.DTO.CommentResponse;
import vpmLimp.model.CommentModel;
import vpmLimp.model.UserModel;
import vpmLimp.repositories.CommentModelRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentModelRepository commentModelRepository;

    public CommentModel createComment(String commentText, UserModel user) {
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

    public CommentModel updateComment(Long id, String newCommentText) {
        CommentModel existingComment = commentModelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        existingComment.setCommentText(newCommentText);
        return commentModelRepository.save(existingComment);
    }

    public void deleteComment(Long id) {
        if (!commentModelRepository.existsById(id)) {
            throw new RuntimeException("Comment not found");
        }
        commentModelRepository.deleteById(id);
    }
}

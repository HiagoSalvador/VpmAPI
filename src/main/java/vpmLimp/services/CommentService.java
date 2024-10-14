package vpmLimp.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vpmLimp.model.CommentModel;
import vpmLimp.repositories.CommentModelRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    @Autowired
    private final CommentModelRepository commentModelRepository;


    public CommentModel saveComment(CommentModel comment) {
        return commentModelRepository.save(comment);
    }


    public List<CommentModel> findAllFilteredByUser(String userEmail) {
        List<CommentModel> comments = commentModelRepository.findAll();

        return comments.stream()
                .filter(comment -> comment.getUser().getEmail().equals(userEmail))
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

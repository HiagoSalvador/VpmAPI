package vpmLimp.validations;

import org.springframework.stereotype.Component;
import vpmLimp.model.CommentModel;
import vpmLimp.model.UserModel;

@Component
public class CommentValidation {

    public void validate(String commentText) {
        if (commentText == null || commentText.trim().isEmpty()) {
            throw new RuntimeException("Comment text cannot be null or empty");
        }
    }

    public void validateOwnership(CommentModel comment, UserModel user) {
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You do not have permission to perform this action on this comment");
        }
    }
}

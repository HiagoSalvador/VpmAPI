package vpmLimp.validations;

import org.springframework.stereotype.Component;
import vpmLimp.DTO.EvaluationRequest;
import vpmLimp.model.EvaluationModel;
import vpmLimp.model.UserModel;

@Component
public class EvaluationValidation {


    public void validateEvaluationRequest(EvaluationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Evaluation request cannot be null.");
        }

        if (request.productName() == null || request.productName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }

        if (request.description() == null || request.description().isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }

        if (request.rating() == null || request.rating() < 1 || request.rating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
    }

    public void validateUserPermission(EvaluationModel evaluation, UserModel user) {
        if (!evaluation.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You do not have permission to modify this evaluation.");
        }
    }
}

package vpmLimp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vpmLimp.DTO.EvaluationRequest;
import vpmLimp.DTO.EvaluationResponse;
import vpmLimp.DTO.UserResponse;
import vpmLimp.model.EvaluationModel;
import vpmLimp.model.UserModel;
import vpmLimp.repositories.EvaluationModelRepository;
import vpmLimp.repositories.UserModelRepository;

import java.util.Optional;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationModelRepository evaluationRepository;

    @Autowired
    private UserModelRepository userRepository;

    public EvaluationResponse createEvaluation(EvaluationRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        Optional<UserModel> optionalUser = userRepository.findByEmail(name);


        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        UserModel user = optionalUser.get();

        EvaluationModel evaluation = new EvaluationModel();
        evaluation.setProductName(request.productName());
        evaluation.setDescription(request.description());
        evaluation.setRating(request.rating());
        evaluation.setUser(user);

        EvaluationModel createdEvaluation = evaluationRepository.save(evaluation);

        return new EvaluationResponse(
                createdEvaluation.getProductName(),
                createdEvaluation.getDescription(),
                createdEvaluation.getRating(),
                new UserResponse.EvaluationUserName(user.getName())
        );
    }
}

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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationService {


    @Autowired
    private EvaluationModelRepository evaluationRepository;

    @Autowired
    private UserModelRepository userRepository;

    private UserModel getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Optional<UserModel> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return optionalUser.get();
    }

    public EvaluationResponse createEvaluation(EvaluationRequest request) {
        UserModel user = getAuthenticatedUser();

        EvaluationModel evaluation = new EvaluationModel();
        evaluation.setProductName(request.productName());
        evaluation.setDescription(request.description());
        evaluation.setRating(request.rating());
        evaluation.setUser(user);

        EvaluationModel createdEvaluation = evaluationRepository.save(evaluation);

        return new EvaluationResponse(
                createdEvaluation.getProductName(),
                createdEvaluation.getDescription(),
                createdEvaluation.getId(),
                createdEvaluation.getRating(),
                new UserResponse.EvaluationUserName(user.getName())
        );
    }

    public EvaluationResponse updateEvaluation(Long id, EvaluationRequest request, String email) {
        UserModel user = getAuthenticatedUser();
        Optional<EvaluationModel> optionalEvaluation = evaluationRepository.findById(id);
        if (optionalEvaluation.isEmpty()) {
            throw new RuntimeException("Evaluation not found");
        }

        EvaluationModel evaluation = optionalEvaluation.get();

        if (!evaluation.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You do not have permission to update this evaluation");
        }

        evaluation.setProductName(request.productName());
        evaluation.setDescription(request.description());
        evaluation.setRating(request.rating());

        EvaluationModel updatedEvaluation = evaluationRepository.save(evaluation);

        return new EvaluationResponse(
                updatedEvaluation.getProductName(),
                updatedEvaluation.getDescription(),
                updatedEvaluation.getId(),
                updatedEvaluation.getRating(),
                new UserResponse.EvaluationUserName(user.getName())
        );
    }

    public void deleteEvaluation(Long id, String email) {
        UserModel user = getAuthenticatedUser();
        Optional<EvaluationModel> optionalEvaluation = evaluationRepository.findById(id);
        if (optionalEvaluation.isEmpty()) {
            throw new RuntimeException("Evaluation not found");
        }

        EvaluationModel evaluation = optionalEvaluation.get();

        if (!evaluation.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You do not have permission to delete this evaluation");
        }

        evaluationRepository.delete(evaluation);
    }

    public List<EvaluationResponse> getAllEvaluations() {
        List<EvaluationModel> evaluations = evaluationRepository.findAll();

        return evaluations.stream()
                .map(evaluation -> new EvaluationResponse(
                        evaluation.getProductName(),
                        evaluation.getDescription(),
                        evaluation.getRating(),
                        new UserResponse.EvaluationUserName(evaluation.getUser().getName())
                ))
                .collect(Collectors.toList());
    }
    }



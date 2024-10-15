package vpmLimp.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@AllArgsConstructor
public class EvaluationService {

    private final EvaluationModelRepository evaluationRepository;
    private final UserModelRepository userRepository;

    public EvaluationResponse createEvaluation(EvaluationRequest request, UserModel user) {
        EvaluationModel evaluation = buildEvaluationFromRequest(request, user);
        EvaluationModel createdEvaluation = evaluationRepository.save(evaluation);
        return buildEvaluationResponse(createdEvaluation, user);
    }

    public EvaluationResponse updateEvaluation(Long id, EvaluationRequest request, UserModel user) {
        EvaluationModel evaluation = findEvaluationById(id);
        verifyUserPermission(evaluation, user);

        updateEvaluationFromRequest(evaluation, request);
        EvaluationModel updatedEvaluation = evaluationRepository.save(evaluation);

        return buildEvaluationResponse(updatedEvaluation, user);
    }

    public void deleteEvaluation(Long id, UserModel user) {
        EvaluationModel evaluation = findEvaluationById(id);
        verifyUserPermission(evaluation, user);
        evaluationRepository.delete(evaluation);
    }

    public List<EvaluationResponse> getAllEvaluations() {
        List<EvaluationModel> evaluations = evaluationRepository.findAll();
        return evaluations.stream()
                .map(evaluation -> buildEvaluationResponse(evaluation, evaluation.getUser()))
                .collect(Collectors.toList());
    }

    private EvaluationModel buildEvaluationFromRequest(EvaluationRequest request, UserModel user) {
        EvaluationModel evaluation = new EvaluationModel();
        evaluation.setProductName(request.productName());
        evaluation.setDescription(request.description());
        evaluation.setRating(request.rating());
        evaluation.setUser(user);
        return evaluation;
    }

    private void updateEvaluationFromRequest(EvaluationModel evaluation, EvaluationRequest request) {
        evaluation.setProductName(request.productName());
        evaluation.setDescription(request.description());
        evaluation.setRating(request.rating());
    }

    private EvaluationModel findEvaluationById(Long id) {
        return evaluationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found"));
    }

    private void verifyUserPermission(EvaluationModel evaluation, UserModel user) {
        if (!evaluation.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You do not have permission to modify this evaluation");
        }
    }

    private EvaluationResponse buildEvaluationResponse(EvaluationModel evaluation, UserModel user) {
        return new EvaluationResponse(
                evaluation.getProductName(),
                evaluation.getDescription(),
                evaluation.getId(),
                evaluation.getRating(),
                new UserResponse.EvaluationUserName(user.getName())
        );
    }
}

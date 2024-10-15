package vpmLimp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vpmLimp.DTO.EvaluationRequest;
import vpmLimp.DTO.EvaluationResponse;
import vpmLimp.model.UserModel;
import vpmLimp.services.EvaluationService;

import java.util.List;

@RestController
@RequestMapping("/evaluations")
@AllArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping
    public ResponseEntity<EvaluationResponse> createEvaluation(@RequestBody EvaluationRequest request, @AuthenticationPrincipal UserModel user) {
        EvaluationResponse newEvaluation = evaluationService.createEvaluation(request, user);
        return ResponseEntity.ok(newEvaluation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluationResponse> updateEvaluation(@PathVariable Long id, @RequestBody EvaluationRequest request, @AuthenticationPrincipal UserModel user) {
        EvaluationResponse updatedEvaluation = evaluationService.updateEvaluation(id, request, user);
        return ResponseEntity.ok(updatedEvaluation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id, @AuthenticationPrincipal UserModel user) {
        evaluationService.deleteEvaluation(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EvaluationResponse>> getAllEvaluations() {
        List<EvaluationResponse> evaluations = evaluationService.getAllEvaluations();
        return ResponseEntity.ok(evaluations);
    }
}

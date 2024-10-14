package vpmLimp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vpmLimp.DTO.EvaluationRequest;
import vpmLimp.DTO.EvaluationResponse;
import vpmLimp.services.EvaluationService;

import java.util.List;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {


    @Autowired
    private EvaluationService evaluationService;

    @PostMapping
    public ResponseEntity<EvaluationResponse> createEvaluation(@RequestBody EvaluationRequest request) {
        EvaluationResponse newEvaluation = evaluationService.createEvaluation(request);
        return ResponseEntity.ok(newEvaluation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluationResponse> updateEvaluation(@PathVariable Long id, @RequestBody EvaluationRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return ResponseEntity.ok(evaluationService.updateEvaluation(id, request, email));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        evaluationService.deleteEvaluation(id, email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EvaluationResponse>> getAllEvaluations() {
        List<EvaluationResponse> evaluations = evaluationService.getAllEvaluations();
        return ResponseEntity.ok(evaluations);
    }
}

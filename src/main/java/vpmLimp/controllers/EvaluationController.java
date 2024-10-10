package vpmLimp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vpmLimp.DTO.EvaluationRequest;
import vpmLimp.DTO.EvaluationResponse;
import vpmLimp.services.EvaluationService;

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
}

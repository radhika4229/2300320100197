package com.radhika.Vehicle_Maintenance.controller;

import com.radhika.Vehicle_Maintenance.dto.EvaluationResponse;
import com.radhika.Vehicle_Maintenance.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/evaluation")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @GetMapping("/optimize")
    public ResponseEntity<EvaluationResponse> optimize() {
        log.info("Received request to optimize depot maintenance tasks");
        EvaluationResponse response = evaluationService.optimizeAllDepots();
        return ResponseEntity.ok(response);
    }
}

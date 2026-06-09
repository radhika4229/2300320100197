package com.radhika.Vehicle_Maintenance.service;


import com.radhika.Vehicle_Maintenance.client.EvaluationApiClient;
import com.radhika.Vehicle_Maintenance.dto.DepotOptimizationResult;
import com.radhika.Vehicle_Maintenance.dto.EvaluationResponse;
import com.radhika.Vehicle_Maintenance.mapper.DepotMapper;
import com.radhika.Vehicle_Maintenance.model.DepotWorkload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationApiClient apiClient;
    private final DepotMapper depotMapper;
    private final OptimizationService optimizationService;

    public EvaluationResponse optimizeAllDepots() {
        log.info("Starting optimization for all depots");


        List<DepotWorkload> depots = apiClient.fetchDepots()
                .stream()
                .map(depotMapper::toModel)
                .toList();

        log.info("Processing {} depots", depots.size());


        List<DepotOptimizationResult> results = depots.stream()
                .map(optimizationService::optimize)
                .toList();


        int grandTotal = results.stream()
                .mapToInt(DepotOptimizationResult::getTotalImpact)
                .sum();

        log.info("Optimization complete. Depots={}, GrandTotalImpact={}",
                results.size(), grandTotal);

        return new EvaluationResponse(results.size(), grandTotal, results);
    }
}
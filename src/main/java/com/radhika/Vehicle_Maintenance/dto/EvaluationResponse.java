package com.radhika.Vehicle_Maintenance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EvaluationResponse {
    private int totalDepots;
    private int grandTotalImpact;
    private List<DepotOptimizationResult> depots;
}

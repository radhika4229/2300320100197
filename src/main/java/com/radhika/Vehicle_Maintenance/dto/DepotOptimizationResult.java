package com.radhika.Vehicle_Maintenance.dto;

import com.radhika.Vehicle_Maintenance.model.MaintenanceTask;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DepotOptimizationResult {
    private String depotId;
    private int availableMechanicHours;
    private int usedHours;
    private int totalImpact;
    private List<MaintenanceTask> selectedTasks;
}
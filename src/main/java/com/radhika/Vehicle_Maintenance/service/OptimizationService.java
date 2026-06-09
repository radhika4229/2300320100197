package com.radhika.Vehicle_Maintenance.service;

import com.radhika.Vehicle_Maintenance.dto.DepotOptimizationResult;
import com.radhika.Vehicle_Maintenance.model.DepotWorkload;
import com.radhika.Vehicle_Maintenance.model.MaintenanceTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class OptimizationService {

    public DepotOptimizationResult optimize(DepotWorkload depot) {
        List<MaintenanceTask> tasks = depot.getTasks();
        int capacity = depot.getMechanicHours();
        int n = tasks.size();

        log.debug("Optimizing depot={} with mechanicHours={} and {} tasks",
                depot.getDepotId(), capacity, n);


        if (n == 0 || capacity <= 0) {
            return buildResult(depot, List.of(), 0, 0);
        }

          int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            MaintenanceTask task = tasks.get(i - 1);
            for (int h = 0; h <= capacity; h++) {
                // Option 1: skip this task
                dp[i][h] = dp[i - 1][h];

                // Option 2: include this task (only if it fits)
                if (task.getDuration() <= h) {
                    int withTask = dp[i - 1][h - task.getDuration()] + task.getImpact();
                    dp[i][h] = Math.max(dp[i][h], withTask);
                }
            }
        }

           List<MaintenanceTask> selectedTasks = new ArrayList<>();
        int remainingHours = capacity;

        for (int i = n; i > 0; i--) {
            if (dp[i][remainingHours] != dp[i - 1][remainingHours]) {
                MaintenanceTask task = tasks.get(i - 1);
                selectedTasks.add(task);
                remainingHours -= task.getDuration();
            }
        }

        Collections.reverse(selectedTasks); // restore original order

        int usedHours = selectedTasks.stream().mapToInt(MaintenanceTask::getDuration).sum();
        int totalImpact = selectedTasks.stream().mapToInt(MaintenanceTask::getImpact).sum();

        log.debug("Depot={} result: usedHours={}/{}, totalImpact={}, selectedTasks={}",
                depot.getDepotId(), usedHours, capacity, totalImpact, selectedTasks.size());

        return buildResult(depot, selectedTasks, usedHours, totalImpact);
    }

    private DepotOptimizationResult buildResult(
            DepotWorkload depot,
            List<MaintenanceTask> selectedTasks,
            int usedHours,
            int totalImpact) {

        return DepotOptimizationResult.builder()
                .depotId(depot.getDepotId())
                .availableMechanicHours(depot.getMechanicHours())
                .usedHours(usedHours)
                .totalImpact(totalImpact)
                .selectedTasks(selectedTasks)
                .build();
    }
}

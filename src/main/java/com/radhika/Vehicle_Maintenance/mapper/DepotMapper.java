package com.radhika.Vehicle_Maintenance.mapper;

import com.radhika.Vehicle_Maintenance.dto.DepotDto;
import com.radhika.Vehicle_Maintenance.model.DepotWorkload;
import com.radhika.Vehicle_Maintenance.model.MaintenanceTask;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
@Component
public class DepotMapper {

    public DepotWorkload toModel(DepotDto dto) {
        List<MaintenanceTask> tasks = (dto.getVehicles() == null)
                ? Collections.emptyList()
                : dto.getVehicles().stream()
                .filter(t -> t != null && t.getTaskID() != null)
                .map(t -> new MaintenanceTask(
                        t.getTaskID(),
                        t.getDuration(),
                        t.getImpact()
                ))
                .toList();

        return new DepotWorkload(dto.getId(), dto.getMechanicHours(), tasks);
    }
}

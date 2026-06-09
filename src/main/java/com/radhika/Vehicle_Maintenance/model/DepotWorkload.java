package com.radhika.Vehicle_Maintenance.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepotWorkload {
    String depotId;
    int mechanicHours;
     List<MaintenanceTask> tasks;
}

package com.radhika.Vehicle_Maintenance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepotDto {
    String id;
    int mechanicHours;
    List<TaskDto> vehicles;

}

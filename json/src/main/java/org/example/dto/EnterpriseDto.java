package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseDto {
    UUID id;
    String name;
    String city;
    Integer productionCapacity;
    List<UUID> vehiclesId;
    List<UUID> driversId;
    String zone;
}

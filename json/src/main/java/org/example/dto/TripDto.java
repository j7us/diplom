package org.example.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDto {
    private UUID id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UUID vehicleId;
    private PointJsonDto startPoint;
    private String startAddress;
    private PointJsonDto endPoint;
    private String endAddress;
    private BigDecimal distance = BigDecimal.ZERO;
}

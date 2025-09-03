package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {
    UUID id;

    LocalDate releaseYear;

    String carNumber;

    Long mileage;

    BigDecimal price;

    UUID brandId;

    String brandName;

    UUID activeDriverId;

    String activeDriverName;

    OffsetDateTime productionDate;
}

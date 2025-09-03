package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {
    UUID id;

    String name;

    String carType;

    Integer capacity;

    String drive;

    BigDecimal weight;
}

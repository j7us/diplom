package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private String name;
    private String interval;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<String, String> result;
}

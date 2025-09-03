package org.example.controller.restControllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.ReportDto;
import org.example.dto.ReportTypeDto;
import org.example.service.report.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping(value = "/reports")
    public List<ReportTypeDto> getReportsList() {
        return reportService.getReports();
    }

    @PostMapping(value = "/reports/generate")
    public ResponseEntity<ReportDto> getReports(@RequestBody Map<String, String> params,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(reportService.buildReport(
                params.get("type"),
                LocalDate.parse(params.get("date_from")),
                LocalDate.parse(params.get("date_to")),
                params.get("interval"),
                params,
                userDetails.getUsername()));
    }

    @PostMapping(value = "/reports/download")
    public ResponseEntity<byte[]> downloadReport(@RequestBody ReportDto reportDto) {
        return ResponseEntity.ok(reportService.buildDocumentFromReport(reportDto));
    }
}

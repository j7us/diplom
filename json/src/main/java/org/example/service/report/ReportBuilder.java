package org.example.service.report;

import org.example.dto.ReportDto;

import java.time.LocalDate;
import java.util.Map;

public abstract class ReportBuilder {

    public ReportDto buildReport(String type,
                                 LocalDate dateFrom,
                                 LocalDate dateTo,
                                 String interval,
                                 Map<String, String> params,
                                 String username) {
        ReportDto report = new ReportDto();

        report.setStartDate(dateFrom);
        report.setEndDate(dateTo);
        report.setName(type);
        report.setInterval(interval);
        report.setResult(buildResult(dateFrom, dateTo, interval, params, username));

        return report;
    }

    public abstract Map<String, String> buildResult(LocalDate dateFrom,
                                                    LocalDate dateTo,
                                                    String interval,
                                                    Map<String, String> params,
                                                    String username);
}

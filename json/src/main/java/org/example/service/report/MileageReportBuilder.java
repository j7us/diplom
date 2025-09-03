package org.example.service.report;

import lombok.RequiredArgsConstructor;
import org.example.dto.PointGeoDto;
import org.example.dto.TripDto;
import org.example.service.TripService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Service("mileage")
@RequiredArgsConstructor
public class MileageReportBuilder extends ReportBuilder {
    private final TripService tripService;

    @Override
    public Map<String, String> buildResult(LocalDate dateFrom,
                                           LocalDate dateTo,
                                           String interval,
                                           Map<String, String> params,
                                           String username) {
        UUID vehicleId = UUID.fromString(params.get("vehicleId"));

        List<TripDto> trips = tripService.getAllTripsWithAdress(
                username, vehicleId, dateFrom.atStartOfDay(), dateTo.atStartOfDay());

        Map<String, String> result = new HashMap<>();
        Function<TripDto, String> intervalExtractor = getGroupByInterval(interval);
        trips.forEach(trip -> {
            String tripInterval = intervalExtractor.apply(trip);
            result.compute(tripInterval,
                    (k, v) -> v == null
                            ? trip.getDistance().toString()
                            : new BigDecimal(v).add(trip.getDistance()).toString());
        });

        return result;
    }

    private Function<TripDto, String> getGroupByInterval(String interval) {
        return switch (interval) {
            case "day" -> (TripDto t) -> t.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
            case "month" -> (TripDto t) -> t.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            case "year" -> (TripDto t) -> t.getStartDate().format(DateTimeFormatter.ofPattern("yyyy"));
            default -> throw new IllegalArgumentException("Invalid interval: " + interval);
        };
    }
}

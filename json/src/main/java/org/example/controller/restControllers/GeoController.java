package org.example.controller.restControllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.PointGeoDto;
import org.example.dto.TripDto;
import org.example.dto.TripWithRouteDto;
import org.example.service.GpxParsingService;
import org.example.service.TripService;
import org.example.service.VehicleLocaltionService;
import org.locationtech.jts.geom.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GeoController {
    private final VehicleLocaltionService vehicleLocaltionService;
    private final TripService tripService;
    private final GpxParsingService gpxParsingService;

    @GetMapping(value = "/geo_points/{id}")
    ResponseEntity<List<?>> getVehiclePoints(@AuthenticationPrincipal UserDetails userDetails,
                                             @PathVariable(value = "id") UUID vehiclesId,
                                             @RequestParam(value = "date_from") LocalDate dateFrom,
                                             @RequestParam(value = "date_to") LocalDate dateTo,
                                             @RequestParam(value = "geo_format", defaultValue = "JSON") String format) {
        return ResponseEntity.ok(vehicleLocaltionService.getLocations(
                vehiclesId,
                dateFrom,
                dateTo,
                format,
                userDetails.getUsername()));
    }

    @GetMapping(value = "vehicles/{id}/trips/route")
    ResponseEntity<List<PointGeoDto>> getAllTripsRoute(@AuthenticationPrincipal UserDetails userDetails,
                                                       @RequestParam(value = "date_from") LocalDateTime dateFrom, 
                                                       @RequestParam(value = "date_to") LocalDateTime dateTo,
                                                       @PathVariable(value = "id") UUID vehicleId) {
        return ResponseEntity.ok(tripService.getVehicleLocationsWithinTripRanges(userDetails.getUsername(), vehicleId, dateFrom, dateTo));
    }

    @GetMapping(value = "vehicles/{id}/trips")
    ResponseEntity<List<TripDto>> getAllTrips(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestParam(value = "date_from") LocalDateTime dateFrom,
                                              @RequestParam(value = "date_to") LocalDateTime dateTo,
                                              @PathVariable(value = "id") UUID vehicleId) {
        return ResponseEntity.ok(tripService.getAllTripsWithAdress(userDetails.getUsername(), vehicleId, dateFrom, dateTo));
    }

    @PostMapping(value = "/vehicles/{vehicleId}/tripsfull")
    ResponseEntity<List<TripWithRouteDto>> getTripsWithFullRoute(@RequestBody List<UUID> tripIds,
                                                                 @PathVariable UUID vehicleId) {
        return ResponseEntity.ok(tripService.getTripsWithFullRouteById(tripIds, vehicleId));
    }

    @PostMapping(value = "/vehicles/{vehicleId}/gpx/upload")
    ResponseEntity<String> uploadGpxFile(@AuthenticationPrincipal UserDetails userDetails,
                                         @PathVariable UUID vehicleId,
                                         @RequestParam("file") MultipartFile file) {
        try {
            gpxParsingService.processGpxFile(vehicleId, file.getInputStream());
            
            return ResponseEntity.ok("GPX файл успешно загружен и обработан");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при обработке файла");
        }
    }
}

package org.example.service;

import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Track;
import lombok.RequiredArgsConstructor;
import org.example.entity.VehicleLocation;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GpxParsingService {
    private final VehicleLocaltionService vehicleLocationService;
    private final TripService tripService;
    
    public void processGpxFile(UUID vehicleId, InputStream gpxFile) {
        try {
            GPX gpx = GPX.Reader.of(GPX.Reader.Mode.LENIENT).read(gpxFile);
            GeometryFactory geometryFactory = new GeometryFactory();


            List<VehicleLocation> savedLocations = gpx.getTracks().stream()
                    .flatMap(Track::segments)
                    .flatMap(segment -> segment.getPoints().stream())
                    .map(wayPoint -> {

                        Point point = geometryFactory.createPoint(
                                new Coordinate(
                                        wayPoint.getLongitude().doubleValue(),
                                        wayPoint.getLatitude().doubleValue()
                                )
                        );

                        LocalDateTime dateTime = wayPoint.getTime()
                                .map(instant -> LocalDateTime.ofInstant(instant, ZoneId.systemDefault()))
                                .orElse(LocalDateTime.now());

                        return vehicleLocationService.addLocationWithTime(vehicleId, point, dateTime);
                    })
                    .sorted(Comparator.comparing(VehicleLocation::getDate))
                    .toList();

            VehicleLocation startLocation = savedLocations.get(0);
            VehicleLocation endLocation = savedLocations.get(savedLocations.size() - 1);

            tripService.createTrip(startLocation.getVehicle(), startLocation.getDate(), endLocation.getDate());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка парсинга GPX файла", e);
        }
    }
}
package org.example.service;

import io.micrometer.observation.Observation;
import lombok.RequiredArgsConstructor;
import org.example.dto.PointJsonDto;
import org.example.entity.Vehicle;
import org.example.entity.VehicleLocation;
import org.example.mapper.VehicleLocationMapper;
import org.example.repository.VehicleLocationRepository;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleLocaltionService {
    private final VehicleLocationRepository vehicleLocationRepository;
    private final VehicleService vehicleService;
    private final VehicleLocationMapper mapper;

    public List<?> getLocations(UUID vehiclesId,
                                LocalDate dateFrom,
                                LocalDate dateTo,
                                String format,
                                String usernmae) {
        Vehicle vehicleById = vehicleService.getVehicleById(usernmae, vehiclesId);

        List<VehicleLocation> locations = vehicleLocationRepository.findAllByVehicleAndDateBetween(
                vehicleById,
                dateFrom.atStartOfDay(),
                dateTo.atStartOfDay());

        return format.equals("GEO_JSON")
                ? buildPointList(locations,  mapper::toPointGeoDto)
                : buildPointList(locations, mapper::toPointJsonDto);
    }

    private <T> List<T> buildPointList(List<VehicleLocation> locations, Function<VehicleLocation, T> mapper) {
        return locations.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public VehicleLocation addLocation(UUID vehicleId, Point point) {
        return addLocation(vehicleId, point, LocalDateTime.now());
    }

    public VehicleLocation addLocationWithTime(UUID vehicleId, Point point, LocalDateTime time) {
        return addLocation(vehicleId, point, time);
    }

    private VehicleLocation addLocation(UUID vehicleId, Point point, LocalDateTime time) {
        Vehicle vehicleById = vehicleService.findById(vehicleId);

        VehicleLocation vehicleLocation = new VehicleLocation();
        vehicleLocation.setId(UUID.randomUUID());
        vehicleLocation.setVehicle(vehicleById);
        vehicleLocation.setLocation(point);
        vehicleLocation.setDate(time);

        return vehicleLocationRepository.save(vehicleLocation);
    }
}

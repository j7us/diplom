package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.entity.Trip;
import org.example.entity.Vehicle;
import org.example.entity.VehicleLocation;
import org.example.mapper.TripMapper;
import org.example.mapper.VehicleLocationMapper;
import org.example.repository.TripRepository;
import org.example.repository.VehicleLocationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static org.example.repository.specification.VehicleLocationSpecifications.withinAnyTripDateRange;

@Service
@RequiredArgsConstructor
public class TripService {
    private final VehicleLocationRepository vehicleLocationRepository;
    private final TripRepository tripRepository;
    private final VehicleLocationMapper pointMapper;
    private final VehicleService vehicleService;
    private final AddressService addressService;
    private final TripMapper tripMapper;

    private List<Trip> getAllTripsInDateRange(String username, UUID vehicleId, LocalDateTime startDate, LocalDateTime endDate) {
        Vehicle vehicleById = vehicleService.getVehicleById(username, vehicleId);
        return tripRepository.findAllByVehicleAndStartDateAfterAndEndDateBefore(vehicleById, startDate, endDate);
    }

    public void createTrip(Vehicle vehicle, LocalDateTime startDate, LocalDateTime endDate) {
        List<Trip> tripInDateRange
                = tripRepository.findAllByVehicleAndStartDateAfterAndEndDateBefore(vehicle, startDate, endDate);

        if (!tripInDateRange.isEmpty()) {
            return;
        }

        Trip trip = new Trip();

        trip.setId(UUID.randomUUID());
        trip.setVehicle(vehicle);
        trip.setStartDate(startDate);
        trip.setEndDate(endDate);
        trip.setDistance(BigDecimal.TEN);

        tripRepository.save(trip);
    }
    
   
    public List<PointGeoDto> getVehicleLocationsWithinTripRanges(String username, UUID vehicleId, LocalDateTime startDate, LocalDateTime endDate) {
        
        List<Trip> trips = getAllTripsInDateRange(username, vehicleId, startDate, endDate);

        if (trips.isEmpty()) {
            return List.of();
        }
        
        return getPointGeoDtos(trips, vehicleId, pointMapper::toPointGeoDto);
    }

    private <T> List<T> getPointGeoDtos(List<Trip> trips, UUID vehicleId, Function<VehicleLocation, T> mapper) {
        List<VehicleLocation> locations = vehicleLocationRepository.findAll(withinAnyTripDateRange(vehicleId, trips));


        return locations.stream()
                .sorted(Comparator.comparing(VehicleLocation::getDate))
                .map(mapper)
                .toList();
    }

    public List<TripDto> getAllTripsWithAdress(String username, UUID vehicleId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Trip> trips = getAllTripsInDateRange(username, vehicleId, startDate, endDate);

        List<TripDto> resultTrips = new ArrayList<>();
        List<PointToAddressDto> address = new ArrayList<>();
        trips.forEach(trip -> {
            TripDto tripDto = tripMapper.toTripDto(trip);
            resultTrips.add(tripDto);

            PointJsonDto startPoint = tripDto.getStartPoint();
            PointJsonDto endPoint = tripDto.getEndPoint();

            address.add(new PointToAddressDto(startPoint.getLongitude(), startPoint.getLatitude()));
            address.add(new PointToAddressDto(endPoint.getLongitude(), endPoint.getLatitude()));
        });

        List<String> allAdress = addressService.getAllAdress(address);

        for (int i = 0, x = 0; i < resultTrips.size(); i++, x+=2) {
            TripDto tripDto = resultTrips.get(i);
            tripDto.setStartAddress(allAdress.get(x));
            tripDto.setEndAddress(allAdress.get(x + 1));
        }

        return resultTrips;
    }

    public List<TripWithRouteDto> getTripsWithFullRouteById(List<UUID> tripIds, UUID vehicleId) {
        List<Trip> trips = tripRepository.findAllByIdInOrderByEndDate(tripIds);

        List<PointJsonDto> pointJsonDtos = getPointGeoDtos(trips, vehicleId, pointMapper::toPointJsonDto);

        return buildTripWithRouteDtos(trips, pointJsonDtos);
    }

    private List<TripWithRouteDto> buildTripWithRouteDtos(List<Trip> trips, List<PointJsonDto> pointJsonDtos) {
        List<TripWithRouteDto> tripWithRouteDtos = new ArrayList<>();
        int tripIndex = 0;
        Trip trip = trips.get(tripIndex);
        TripWithRouteDto currentTripWithRouteDto = new TripWithRouteDto(trip.getId(), new ArrayList<>());

        for (int i = 0; i < pointJsonDtos.size(); i++) {
            PointJsonDto pointJsonDto = pointJsonDtos.get(i);
            if (pointJsonDto.getDateTime().isBefore(trip.getEndDate())
                    || pointJsonDto.getDateTime().equals(trip.getEndDate())) {
                currentTripWithRouteDto.getPoint().add(pointJsonDto);
            } else {
                tripWithRouteDtos.add(currentTripWithRouteDto);
                tripIndex++;
                trip = trips.get(tripIndex);
                currentTripWithRouteDto = new TripWithRouteDto(trip.getId(), new ArrayList<>());
                i--;
            }
        }

        tripWithRouteDtos.add(currentTripWithRouteDto);

        return tripWithRouteDtos;
    }
}

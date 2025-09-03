package org.example.mapper;

import org.example.dto.TripDto;
import org.example.entity.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {VehicleLocationMapper.class})
public interface TripMapper {

    @Mapping(target = "vehicleId", source = "vehicle.id")
    @Mapping(target = "startPoint", source = "startLocation")
    @Mapping(target = "endPoint", source = "endLocation")
    TripDto toTripDto(Trip trip);

    List<TripDto> toTripDtoList(List<Trip> trips);
}

package org.example.mapper;

import org.example.dto.PointGeoDto;
import org.example.dto.PointJsonDto;
import org.example.entity.VehicleLocation;
import org.locationtech.jts.geom.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface VehicleLocationMapper {

    @Mapping(target = "dateTime", source = "date")
    PointGeoDto toPointGeoDto(VehicleLocation vehicleLocation);

    @Mapping(target = "longitude", source = "location", qualifiedByName = "toLongitude")
    @Mapping(target = "latitude", source = "location", qualifiedByName = "toLatitude")
    @Mapping(target = "dateTime", source = "date")
    PointJsonDto toPointJsonDto(VehicleLocation vehicleLocation);

    @Named("toLongitude")
    default String toLongitude(Point location) {
        return String.valueOf(location.getY());
    }

    @Named("toLatitude")
    default String toLatitude(Point location) {
        return String.valueOf(location.getX());
    }
}

package org.example.mapper;

import org.example.dto.VehicleDto;
import org.example.entity.DriverVehicle;
import org.example.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "activeDriverId", source = "drivers", qualifiedByName = "getActiveId")
    VehicleDto toVehicleDto(Vehicle vehicle);

    List<VehicleDto> toDtoList(List<Vehicle> vehicles);

    @Named("getActiveId")
    default Long getActiveId(List<DriverVehicle> drivers) {
        return drivers.stream()
                .filter(DriverVehicle::getActive)
                .map(dv -> dv.getDriver().getId())
                .findFirst()
                .orElse(null);
    }

    @Mapping(target = "id", ignore = true)
    void updateEntity(VehicleDto dto, @MappingTarget Vehicle vehicle);

    @Mapping(target = "id", ignore = true)
    Vehicle toEntity(VehicleDto dto);
}

package org.example.mapper;

import org.example.dto.EnterpriseDto;
import org.example.dto.VehicleDto;
import org.example.entity.Driver;
import org.example.entity.Enterprise;
import org.example.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EnterpriseMapper {

    @Mapping(target = "vehiclesId", source = "vehicles", qualifiedByName = "extractVehiclesId")
    @Mapping(target = "driversId", source = "drivers", qualifiedByName = "extractDriversId")
    EnterpriseDto toDto(Enterprise enterprise);

    List<EnterpriseDto> toListDto(List<Enterprise> enterprises);

    Enterprise toEntity(EnterpriseDto enterpriseDto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(EnterpriseDto dto, @MappingTarget Enterprise vehicle);

    @Named("extractVehiclesId")
    default List<UUID> extractVehiclesId(List<Vehicle> vehicles) {
        if (CollectionUtils.isEmpty(vehicles)) {
            return Collections.emptyList();
        }

        return vehicles.stream()
                .map(Vehicle::getId)
                .toList();
    }

    @Named("extractDriversId")
    default List<UUID> extractDriversId(List<Driver> drivers) {
        if (CollectionUtils.isEmpty(drivers)) {
            return Collections.emptyList();
        }

        return drivers.stream()
                .map(Driver::getId)
                .toList();
    }
}

package org.example.mapper;

import org.example.dto.EnterpriseDto;
import org.example.entity.Driver;
import org.example.entity.Enterprise;
import org.example.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnterpriseMapper {

    @Mapping(target = "vehiclesId", source = "vehicles", qualifiedByName = "extractVehiclesId")
    @Mapping(target = "driversId", source = "drivers", qualifiedByName = "extractDriversId")
    EnterpriseDto toDto(Enterprise enterprise);

    List<EnterpriseDto> toListDto(List<Enterprise> enterprises);

    @Named("extractVehiclesId")
    default List<Long> extractVehiclesId(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(Vehicle::getId)
                .toList();
    }

    @Named("extractDriversId")
    default List<Long> extractDriversId(List<Driver> drivers) {
        return drivers.stream()
                .map(Driver::getId)
                .toList();
    }
}

package org.example.mapper;

import org.example.dto.DriverDto;
import org.example.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    DriverDto toDto(Driver driver);

    List<DriverDto> toDtoList(List<Driver> drivers);
}

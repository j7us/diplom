package org.example.mapper;

import org.example.dto.VehicleDto;
import org.example.entity.Driver;
import org.example.entity.DriverVehicle;
import org.example.entity.Vehicle;
import org.example.entity.VehicleLocation;
import org.locationtech.jts.geom.Point;
import org.mapstruct.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "mileage", source = "mileage", qualifiedByName = "mileageConverter")
    @Mapping(target = "productionDate", ignore = true)
    VehicleDto toVehicleDto(Vehicle vehicle);

    List<VehicleDto> toDtoList(List<Vehicle> vehicles);

    @Named("mileageConverter")
    default Long mileageConverter(BigDecimal mileage) {
        return Optional.ofNullable(mileage).map(BigDecimal::longValue).orElse(0L);
    }

    @AfterMapping
    default void setActiveDriverId(Vehicle vehicle, @MappingTarget VehicleDto vehicleDto) {
        Optional<Driver> activeDriver = vehicle.getDrivers().stream()
                .filter(DriverVehicle::getActive)
                .map(DriverVehicle::getDriver)
                .findFirst();

        if (activeDriver.isPresent()) {
            Driver driver = activeDriver.get();
            vehicleDto.setActiveDriverId(driver.getId());
            vehicleDto.setActiveDriverName(driver.getName());
        }

        if (vehicle.getProductionDate() != null) {
            String zone = vehicle.getEnterprise().getZone();

            ZonedDateTime zonedDateTime = vehicle.getProductionDate().atZone(ZoneId.of("UTC"));

            if (StringUtils.hasText(zone)) {
                zonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of(zone));
            }
            vehicleDto.setProductionDate(zonedDateTime.toOffsetDateTime());
        }
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mileage", source = "mileage", qualifiedByName = "toMileageConverter")
    @Mapping(target = "productionDate", source = "productionDate", qualifiedByName = "dateConverter")
    void updateEntity(VehicleDto dto, @MappingTarget Vehicle vehicle);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mileage", source = "mileage", qualifiedByName = "toMileageConverter")
    @Mapping(target = "productionDate", source = "productionDate", qualifiedByName = "dateConverter")
    Vehicle toEntity(VehicleDto dto);

    @Named("toMileageConverter")
    default BigDecimal toMileageConverter(Long mileage) {
        return BigDecimal.valueOf(mileage);
    }

    @Named("dateConverter")
    default LocalDateTime toLocalDateTime(OffsetDateTime prodDate) {
        return prodDate.withOffsetSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }
}

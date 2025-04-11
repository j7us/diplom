package org.example.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dto.EnterpriseWithVehiclesDto;
import org.example.dto.VehicleForEnterpriseDto;
import org.example.entity.EnterpriseWithVehicles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnterprWithVehMapper {
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Mapping(target = "vehicles", source = "vehicles", qualifiedByName = "buildFromJson")
    EnterpriseWithVehiclesDto toDto(EnterpriseWithVehicles enter);

    List<EnterpriseWithVehiclesDto> toListDto(List<EnterpriseWithVehicles> enters);

    @Named("buildFromJson")
    default List<Integer> buildFromJson(String vehicles) {
        try{
            return objectMapper.readValue(vehicles, new TypeReference<List<Integer>>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

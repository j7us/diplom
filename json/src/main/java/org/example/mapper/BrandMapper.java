package org.example.mapper;

import org.example.dto.BrandDto;
import org.example.entity.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    BrandDto toDto(Brand brand);

    List<BrandDto> toDtoList(List<Brand> brands);
}

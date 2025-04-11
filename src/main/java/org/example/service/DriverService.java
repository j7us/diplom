package org.example.service;

import org.example.dto.DriverDto;
import org.example.mapper.DriverMapper;
import org.example.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    public DriverService(DriverRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    public List<DriverDto> getAllDrivers() {
        return driverMapper.toDtoList(driverRepository.findAll());
    }

    public DriverDto getById(Long id) {
        return driverMapper.toDto(driverRepository.findById(id).orElseThrow(RuntimeException::new));
    }
}

package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.DriverDto;
import org.example.entity.Driver;
import org.example.mapper.DriverMapper;
import org.example.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final EnterpriseService enterpriseService;

    public List<DriverDto> getAllDrivers(String username) {
        return driverMapper.toDtoList(
                driverRepository.findAllByEnterpriseIn(enterpriseService.getAllEnterprise(username)));
    }

    public DriverDto getById(UUID id) {
        return driverMapper.toDto(driverRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }
}

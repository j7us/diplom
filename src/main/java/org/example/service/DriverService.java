package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.DriverDto;
import org.example.mapper.DriverMapper;
import org.example.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public DriverDto getById(Long id) {
        return driverMapper.toDto(driverRepository.findById(id).orElseThrow(RuntimeException::new));
    }
}

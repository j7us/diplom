package org.example.service;

import org.example.dto.VehicleDto;
import org.example.entity.Vehicle;
import org.example.mapper.VehicleMapper;
import org.example.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper mapper;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper mapper) {
        this.vehicleRepository = vehicleRepository;
        this.mapper = mapper;
    }

    public List<VehicleDto> getAllInDto() {
        return mapper.toDtoList(vehicleRepository.findAll());
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public VehicleDto findByIdDto(Long id) {
        return mapper.toVehicleDto(vehicleRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Transactional
    public void deleteById(Long id) {
        vehicleRepository.deleteById(id);
    }

    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @Transactional
    public void deleteByBrandId(Long id) {
        vehicleRepository.deleteAllByBrandId(id);
    }
}

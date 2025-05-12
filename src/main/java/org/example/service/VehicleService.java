package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.VehicleDto;
import org.example.entity.Brand;
import org.example.entity.Enterprise;
import org.example.entity.Vehicle;
import org.example.mapper.VehicleMapper;
import org.example.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper mapper;
    private final EnterpriseService enterpriseService;
    private final BrandService brandService;

    public List<VehicleDto> getAllInDto(String username) {
        return mapper.toDtoList(
                vehicleRepository.findAllByEnterpriseInOrEnterpriseIsNull(enterpriseService.getAllEnterprise(username)));
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public VehicleDto findByIdDto(Long id) {
        return mapper.toVehicleDto(vehicleRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Transactional
    public void deleteById(Long id, String username) {
        List<Enterprise> enterprises = enterpriseService.getAllEnterprise(username);

        if (CollectionUtils.isEmpty(enterprises)) {
            throw new RuntimeException("No enterprises");
        }

        vehicleRepository.deleteByEnterpriseInOrEnterpriseIsNullAndId(enterprises, id);
    }

    public VehicleDto updateVehicle(VehicleDto updated, Long id, String username) {
        Vehicle currentVersion = vehicleRepository.findByEnterpriseInOrEnterpriseIsNullAndId(
                enterpriseService.getAllEnterprise(username), id)
                .orElseThrow();

        mapper.updateEntity(updated, currentVersion);

        if (!checkRelationEntity(currentVersion.getBrand(), Brand::getId, updated.getBrandId())) {
            currentVersion.setBrand(updated.getBrandId() == null
                    ? null
                    : brandService.findById(updated.getId()));
        }

        vehicleRepository.flush();

        return mapper.toVehicleDto(currentVersion);
    }

    public VehicleDto createVehicle(VehicleDto vehicleDto, String username, Long entId) {
        Vehicle entity = mapper.toEntity(vehicleDto);

        if (vehicleDto.getBrandId() != null) {
            entity.setBrand(brandService.findById(vehicleDto.getBrandId()));
        }

        entity.setEnterprise(enterpriseService.getEntityById(username, entId));

        Vehicle save = vehicleRepository.save(entity);

        vehicleRepository.flush();

        return mapper.toVehicleDto(save);
    }

    private <T, A> boolean checkRelationEntity(T relation, Function<T, A> extractId, A newId) {
        if ((relation == null && newId == null) || (relation != null && extractId.apply(relation).equals(newId))) {
            return true;
        }

        return false;
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

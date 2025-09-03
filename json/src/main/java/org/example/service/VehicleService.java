package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.VehicleDto;
import org.example.entity.Brand;
import org.example.entity.Enterprise;
import org.example.entity.Vehicle;
import org.example.exception.NoAccessException;
import org.example.mapper.VehicleMapper;
import org.example.repository.DriverVehicleRepository;
import org.example.repository.VehicleRepository;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final DriverVehicleRepository driverVehicleRepository;
    private final VehicleMapper mapper;
    private final EnterpriseService enterpriseService;
    private final BrandService brandService;

    public List<VehicleDto> getAll(String username) {
        return mapper.toDtoList(
                vehicleRepository.findAllByEnterpriseIn(enterpriseService.getAllEnterprise(username)));
    }

    public List<VehicleDto> getAllByEnterprise(String username, UUID enterpriseId) {
        return mapper.toDtoList(
                vehicleRepository.findAllByEnterprise(enterpriseService.getEntityById(username, enterpriseId)));
    }

    public Page<VehicleDto> getAllByPage(String username, PageRequest pageRequest) {
        return vehicleRepository
                .findAllByEnterpriseIn(enterpriseService.getAllEnterprise(username), pageRequest)
                .map(mapper::toVehicleDto);
    }

    public Page<VehicleDto> getAllByEnterpriseAndPage(String username, UUID enterpriseId, PageRequest pageRequest) {
        Enterprise enterprise = enterpriseService.getEntityById(username, enterpriseId);

        if (enterprise == null) {
            throw new NoAccessException();
        }

        return vehicleRepository
                .findAllByEnterprise(enterprise, pageRequest)
                .map(mapper::toVehicleDto);
    }

    public VehicleDto findByIdDto(String userName, UUID id) {
        return mapper.toVehicleDto(vehicleRepository.findByEnterpriseInAndId(enterpriseService.getAllEnterprise(userName),id)
                .orElseThrow(RuntimeException::new));
    }

    public Vehicle getVehicleById(String userName, UUID id) {
        return vehicleRepository.findByEnterpriseInAndId(enterpriseService.getAllEnterprise(userName),id)
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void deleteById(UUID id, String username) {
        boolean hasAccess = enterpriseService.getAllEnterprise(username)
                .stream().flatMap(e -> e.getVehicles().stream())
                .anyMatch(v -> v.getId().equals(id));

        if (!hasAccess) {
            throw new RuntimeException("No enterprises");
        }

        driverVehicleRepository.deleteAllByVehicleId(id);

        vehicleRepository.deleteById(id);
    }

    public VehicleDto updateVehicle(VehicleDto updated, UUID id, String username) {
        Vehicle currentVersion = vehicleRepository.findByEnterpriseInAndId(
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

    public VehicleDto createVehicle(VehicleDto vehicleDto, String username, UUID entId) {
        Vehicle entity = mapper.toEntity(vehicleDto);
        entity.setId(UUID.randomUUID());

        if (vehicleDto.getBrandId() != null) {
            entity.setBrand(brandService.findById(vehicleDto.getBrandId()));
        }

        //TODO исправить что будет, если enterprise не доступен
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

    public Vehicle findById(UUID id) {
        return vehicleRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
}

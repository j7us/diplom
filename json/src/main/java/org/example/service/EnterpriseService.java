package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.example.dto.EnterpriseDto;
import org.example.dto.ZoneDto;
import org.example.entity.Enterprise;
import org.example.entity.Manager;
import org.example.entity.ManagerEnterprise;
import org.example.exception.ConflictException;
import org.example.exception.NoAccessException;
import org.example.mapper.EnterpriseMapper;
import org.example.repository.EnterpriseRepository;
import org.example.repository.ManagerEnterpriseRepository;
import org.example.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnterpriseService {
    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseMapper enterpriseMapper;
    private final ManagerRepository managerRepository;
    private final ManagerEnterpriseRepository meRepository;

    public List<EnterpriseDto> getAllEnterpriseDto(String username) {
        return enterpriseMapper.toListDto(enterpriseRepository.getAllWithUserRestriction(username));
    }

    public List<Enterprise> getAllEnterprise(String username) {
        return enterpriseRepository.getAllWithUserRestriction(username);
    }

    public EnterpriseDto getById(String username, UUID id) {
        return enterpriseMapper.toDto(enterpriseRepository.getByIdWithUserRestriction(username, id).orElseThrow(NoAccessException::new));
    }

    public Enterprise getEntityById(String username, UUID id) {
        return enterpriseRepository.getByIdWithUserRestriction(username, id).orElseThrow(NoAccessException::new);
    }

    public Enterprise getEntityById(UUID id) {
        return enterpriseRepository.getEnterpriseById(id).orElseThrow(NoAccessException::new);
    }

    @Transactional
    public void deleteById(String username, UUID id) {
        Enterprise enterprise = getEntityById(username, id);

        meRepository.deleteAllByEnterpriseId(id);

        try {
            enterpriseRepository.delete(enterprise);
            enterpriseRepository.flush();
        } catch (Exception e) {
            System.out.println(e.getClass());
            throw new ConflictException();
        }
    }

    @Transactional
    public EnterpriseDto updateEnterprise(EnterpriseDto enterpriseDto, String username, UUID id) {
        Enterprise enterprise = getEntityById(username, id);

        enterpriseMapper.updateEntity(enterpriseDto, enterprise);

        enterpriseRepository.flush();

        return enterpriseMapper.toDto(enterprise);
    }

    @Transactional
    public EnterpriseDto updateZone(ZoneDto zoneDto, String username, UUID id) {
        Enterprise enterprise = getEntityById(username, id);

        enterprise.setZone(zoneDto.getZone());

        return enterpriseMapper.toDto(enterprise);
    }

    @Transactional
    public EnterpriseDto saveEnterprise(EnterpriseDto enterpriseDto, String username) {
        Enterprise entity = enterpriseMapper.toEntity(enterpriseDto);
        Enterprise save = enterpriseRepository.save(entity);

        Manager manager = managerRepository.findByUsername(username).orElseThrow();

        ManagerEnterprise me = new ManagerEnterprise();
        me.setManager(manager);
        me.setEnterprise(save);

        meRepository.save(me);

        enterpriseRepository.flush();

        return enterpriseMapper.toDto(save);
    }
}

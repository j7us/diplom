package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.EnterpriseDto;
import org.example.dto.EnterpriseWithVehiclesDto;
import org.example.entity.EnterpriseWithVehicles;
import org.example.mapper.EnterprWithVehMapper;
import org.example.mapper.EnterpriseMapper;
import org.example.repository.EnterpriseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterpriseService {
    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseMapper enterpriseMapper;
    private final EnterprWithVehMapper enterprWithVehMapper;

    public EnterpriseService(EnterpriseRepository enterpriseRepository, EnterpriseMapper enterpriseMapper, EnterprWithVehMapper enterprWithVehMapper) {
        this.enterpriseRepository = enterpriseRepository;
        this.enterpriseMapper = enterpriseMapper;
        this.enterprWithVehMapper = enterprWithVehMapper;
    }

    public List<EnterpriseDto> getAllEnterprise() {
        return enterpriseMapper.toListDto(enterpriseRepository.findAll());
    }

    public EnterpriseDto getById(Long id) {
        return enterpriseMapper.toDto(enterpriseRepository.findById(id).orElseThrow(RuntimeException::new));
    }
}

package org.example.service;

import org.example.dto.BrandDto;
import org.example.entity.Brand;
import org.example.mapper.BrandMapper;
import org.example.repository.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper mapper;

    public BrandService(BrandRepository brandRepository, BrandMapper mapper) {
        this.brandRepository = brandRepository;
        this.mapper = mapper;
    }

    public List<BrandDto> getAllDto() {
        return mapper.toDtoList(brandRepository.findAll());
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }

    public BrandDto findByIdDto(Long id) {
        return mapper.toDto(brandRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    public Brand findById(Long id) {
        return brandRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void save(Brand brand) {
        brandRepository.save(brand);
    }
}

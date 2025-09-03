package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Brand;
import org.example.repository.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }

    public Brand findById(Long id) {
        return brandRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void save(Brand brand) {
        brandRepository.save(brand);
    }
}

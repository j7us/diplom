package org.example.controller;

import org.example.dto.*;
import org.example.entity.EnterpriseWithVehicles;
import org.example.service.BrandService;
import org.example.service.DriverService;
import org.example.service.EnterpriseService;
import org.example.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class JsonController {

    private final VehicleService vehicleService;
    private final BrandService brandService;
    private final EnterpriseService enterpriseService;
    private final DriverService driverService;

    public JsonController(VehicleService vehicleService, BrandService brandService, EnterpriseService enterpriseService, DriverService driverService) {
        this.vehicleService = vehicleService;
        this.brandService = brandService;
        this.enterpriseService = enterpriseService;
        this.driverService = driverService;
    }

    @GetMapping(value = "/vehicles")
    ResponseEntity<List<VehicleDto>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllInDto());
    }

    @GetMapping(value = "/vehicles/{id}/")
    ResponseEntity<VehicleDto> getVehicleById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(vehicleService.findByIdDto(id));
    }

    @GetMapping(value = "/brands")
    ResponseEntity<List<BrandDto>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllDto());
    }

    @GetMapping(value = "/brands/{id}/")
    ResponseEntity<BrandDto> getBrandById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(brandService.findByIdDto(id));
    }

    @GetMapping(value = "/enterprises")
    ResponseEntity<List<EnterpriseDto>> getAllEnterprises() {
        return ResponseEntity.ok(enterpriseService.getAllEnterprise());
    }

    @GetMapping(value = "/enterprises/{id}/")
    ResponseEntity<EnterpriseDto> getEnterpriseById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(enterpriseService.getById(id));
    }

    @GetMapping(value = "/drivers")
    ResponseEntity<List<DriverDto>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping(value = "/drivers/{id}/")
    ResponseEntity<DriverDto> getDriverById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(driverService.getById(id));
    }
}

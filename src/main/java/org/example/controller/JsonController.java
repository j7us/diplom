package org.example.controller;

import org.example.dto.*;
import org.example.service.BrandService;
import org.example.service.DriverService;
import org.example.service.EnterpriseService;
import org.example.service.VehicleService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    ResponseEntity<List<VehicleDto>> getAllVehicles(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(vehicleService.getAllInDto(userDetails.getUsername()));
    }

    @GetMapping(value = "/vehicles/{id}/")
    ResponseEntity<VehicleDto> getVehicleById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(vehicleService.findByIdDto(id));
    }

    @PutMapping(value = "/vehicles/{id}")
    ResponseEntity<VehicleDto> updateVehicle(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestBody VehicleDto vehicleDto,
                                             @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicleDto, id, userDetails.getUsername()));
    }

    @PostMapping(value = "/vehicles/create/{entId}")
    ResponseEntity<VehicleDto> createVehicle(@RequestBody VehicleDto vehicleDto,
                                             @AuthenticationPrincipal UserDetails userDetails,
                                             @PathVariable(value = "entId") Long entId) {
        return ResponseEntity.status(201).body(vehicleService.createVehicle(vehicleDto, userDetails.getUsername(), entId));
    }

    @DeleteMapping(value = "/vehicles/{id}")
    ResponseEntity<?> deleteById(@PathVariable(value = "id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        vehicleService.deleteById(id, userDetails.getUsername());
        return ResponseEntity.status(204).build();
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
    ResponseEntity<List<EnterpriseDto>> getAllEnterprises(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(enterpriseService.getAllEnterpriseDto(userDetails.getUsername()));
    }

    @GetMapping(value = "/enterprises/{id}")
    ResponseEntity<EnterpriseDto> getEnterpriseById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(enterpriseService.getById(userDetails.getUsername(), id));
    }

    @PostMapping(value = "/enterprises/create")
    ResponseEntity<EnterpriseDto> saveEnterprise(@AuthenticationPrincipal UserDetails userDetails, @RequestBody EnterpriseDto enterpriseDto) {
        return ResponseEntity.status(201).body(enterpriseService.saveEnterprise(enterpriseDto, userDetails.getUsername()));
    }

    @PutMapping(value = "/enterprises/{id}")
    ResponseEntity<EnterpriseDto> updateEnterprise(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody EnterpriseDto enterpriseDto,
                                                   @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(enterpriseService.updateEnterprise(enterpriseDto, userDetails.getUsername(), id));
    }

    @DeleteMapping(value = "/enterprises/{id}")
    ResponseEntity deleteEnterprise(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(value = "id") Long id) {
        enterpriseService.deleteById(userDetails.getUsername(), id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping(value = "/drivers")
    ResponseEntity<List<DriverDto>> getAllDrivers(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(driverService.getAllDrivers(userDetails.getUsername()));
    }

    @GetMapping(value = "/drivers/{id}/")
    ResponseEntity<DriverDto> getDriverById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(driverService.getById(id));
    }
}

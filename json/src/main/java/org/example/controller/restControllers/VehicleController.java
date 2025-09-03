package org.example.controller.restControllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.BrandDto;
import org.example.dto.VehicleDto;
import org.example.service.BrandService;
import org.example.service.JobExecutionService;
import org.example.service.VehicleService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    private final BrandService brandService;
    private final JobExecutionService jobExecutionService;

    @GetMapping(value = "/vehicles")
    ResponseEntity<List<VehicleDto>> getAllVehicles(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(vehicleService.getAll(userDetails.getUsername()));
    }

    @GetMapping(value = "/vehicles/by_enterprise")
    ResponseEntity<List<VehicleDto>> getAllVehiclesByEnterpriseId(@AuthenticationPrincipal UserDetails userDetails,
                                                                  @RequestParam(value = "enterprise_id") UUID enterpriseId) {
        return ResponseEntity.ok(vehicleService.getAllByEnterprise(userDetails.getUsername(), enterpriseId));
    }

    @GetMapping(value = "/vehicles/page")
    ResponseEntity<Page<VehicleDto>> getVehiclesByPage(@AuthenticationPrincipal UserDetails userDetails,
                                                       @RequestParam(value = "page") Integer page,
                                                       @RequestParam(value = "size") Integer size) {
        return ResponseEntity.ok(vehicleService.getAllByPage(userDetails.getUsername(), PageRequest.of(page, size)));
    }

    @GetMapping(value = "/vehicles/export/csv")
    ResponseEntity<Resource> exportCsvEnterprise(@AuthenticationPrincipal UserDetails userDetails) throws IOException {
        Path tempFile;
        try {
            tempFile = Files.createTempFile("export-", ".csv");
            jobExecutionService.exportVehicleCsvJob(tempFile.toAbsolutePath().toString(), userDetails.getUsername());
            FileSystemResource fileSystemResource = new FileSystemResource(tempFile.toFile());
            return ResponseEntity.ok(fileSystemResource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/vehicles_enter/page")
    ResponseEntity<Page<VehicleDto>> getVehiclesByPage(@AuthenticationPrincipal UserDetails userDetails,
                                                       @RequestParam(value = "page") Integer page,
                                                       @RequestParam(value = "size") Integer size,
                                                       @RequestParam(value = "enterpriseId") UUID enterpriseId) {
        return ResponseEntity.ok(
                vehicleService.getAllByEnterpriseAndPage(
                        userDetails.getUsername(),
                        enterpriseId,
                        PageRequest.of(page, size)));
    }

    @GetMapping(value = "/vehicles/{id}")
    ResponseEntity<VehicleDto> getVehicleById(@PathVariable(value = "id") UUID id,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(vehicleService.findByIdDto(userDetails.getUsername(), id));
    }

    @PutMapping(value = "/vehicles/{id}")
    ResponseEntity<VehicleDto> updateVehicle(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestBody VehicleDto vehicleDto,
                                             @PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicleDto, id, userDetails.getUsername()));
    }

    @PostMapping(value = "/vehicles/create/{entId}")
    ResponseEntity<VehicleDto> createVehicle(@RequestBody VehicleDto vehicleDto,
                                             @AuthenticationPrincipal UserDetails userDetails,
                                             @PathVariable(value = "entId") UUID entId) {
        return ResponseEntity.status(201).body(vehicleService.createVehicle(vehicleDto, userDetails.getUsername(), entId));
    }

    @DeleteMapping(value = "/vehicles/{id}")
    ResponseEntity<?> deleteById(@PathVariable(value = "id") UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        vehicleService.deleteById(id, userDetails.getUsername());
        return ResponseEntity.status(204).build();
    }

    @GetMapping(value = "/brands")
    ResponseEntity<List<BrandDto>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllDto());
    }

    @GetMapping(value = "/brands/{id}/")
    ResponseEntity<BrandDto> getBrandById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(brandService.findByIdDto(id));
    }
}

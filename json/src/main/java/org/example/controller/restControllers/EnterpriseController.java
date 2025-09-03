package org.example.controller.restControllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.EnterpriseDto;
import org.example.dto.ZoneDto;
import org.example.service.EnterpriseService;
import org.example.service.JobExecutionService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EnterpriseController {
    private final EnterpriseService enterpriseService;
    private final JobExecutionService jobExecutionService;

    @PostMapping(value = "/enterprise/download/csv")
    ResponseEntity downloadCsvFile(@RequestParam("file")MultipartFile file,
                                   @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("upload-", ".csv");
            file.transferTo(tempFile);
            jobExecutionService.executeEnterpriseCsvJob(tempFile.toAbsolutePath().toString(), userDetails.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (tempFile != null) {
                Files.deleteIfExists(tempFile);
            }
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/enterprise/download/json")
    ResponseEntity downloadJsonFile(@RequestParam("file")MultipartFile file,
                                    @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("upload-", ".json");
            file.transferTo(tempFile);
            jobExecutionService.executeEnterpriseJsonJob(tempFile.toAbsolutePath().toString(), userDetails.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (tempFile != null) {
                Files.deleteIfExists(tempFile);
            }
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/enterprise/export/json")
    ResponseEntity<Resource> exportJsonEnterprise(@AuthenticationPrincipal UserDetails userDetails) throws IOException {
        Path tempFile;
        try {
            tempFile = Files.createTempFile("export-", ".json");
            jobExecutionService.exportEnterpriseJsonJob(tempFile.toAbsolutePath().toString(), userDetails.getUsername());
            FileSystemResource fileSystemResource = new FileSystemResource(tempFile.toFile());
            return ResponseEntity.ok(fileSystemResource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/enterprise/export/csv")
    ResponseEntity<Resource> exportCsvEnterprise(@AuthenticationPrincipal UserDetails userDetails) throws IOException {
        Path tempFile;
        try {
            tempFile = Files.createTempFile("export-", ".csv");
            jobExecutionService.exportEnterpriseCsvJob(tempFile.toAbsolutePath().toString(), userDetails.getUsername());
            FileSystemResource fileSystemResource = new FileSystemResource(tempFile.toFile());
            return ResponseEntity.ok(fileSystemResource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/enterprises")
    ResponseEntity<List<EnterpriseDto>> getAllEnterprises(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(enterpriseService.getAllEnterpriseDto(userDetails.getUsername()));
    }

    @GetMapping(value = "/enterprises/{id}")
    ResponseEntity<EnterpriseDto> getEnterpriseById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(enterpriseService.getById(userDetails.getUsername(), id));
    }

    @PostMapping(value = "/enterprises/create")
    ResponseEntity<EnterpriseDto> saveEnterprise(@AuthenticationPrincipal UserDetails userDetails, @RequestBody EnterpriseDto enterpriseDto) {
        return ResponseEntity.status(201).body(enterpriseService.saveEnterprise(enterpriseDto, userDetails.getUsername()));
    }

    @PutMapping(value = "/enterprises/{id}")
    ResponseEntity<EnterpriseDto> updateEnterprise(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody EnterpriseDto enterpriseDto,
                                                   @PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(enterpriseService.updateEnterprise(enterpriseDto, userDetails.getUsername(), id));
    }

    @PatchMapping(value = "/enterprises/{id}/zone")
    ResponseEntity<EnterpriseDto> updateEnterprise(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody ZoneDto zoneDto,
                                                   @PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(enterpriseService.updateZone(zoneDto, userDetails.getUsername(), id));
    }

    @DeleteMapping(value = "/enterprises/{id}")
    ResponseEntity deleteEnterprise(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(value = "id") UUID id) {
        enterpriseService.deleteById(userDetails.getUsername(), id);
        return ResponseEntity.status(204).build();
    }
}

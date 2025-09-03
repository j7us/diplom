package org.example.controller.restControllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.DriverDto;
import org.example.service.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @GetMapping(value = "/drivers")
    ResponseEntity<List<DriverDto>> getAllDrivers(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(driverService.getAllDrivers(userDetails.getUsername()));
    }

    @GetMapping(value = "/drivers/{id}/")
    ResponseEntity<DriverDto> getDriverById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(driverService.getById(id));
    }
}

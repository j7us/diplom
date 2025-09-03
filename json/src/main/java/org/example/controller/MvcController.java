package org.example.controller;

import org.example.entity.Brand;
import org.example.entity.Vehicle;
import org.example.service.BrandService;
import org.example.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class MvcController {

    @GetMapping("/login/manager")
    public String loginManager() {
        return "login";
    }

    @GetMapping("/view/vehicles/{id}")
    public String vehicleDetails() {
        return "vehicle_details";
    }

    @GetMapping("/view/enterprises")
    public String getEnterprises() {
        return "enterprises";
    }

    @GetMapping("/view/enterprises/{id}/veh_list")
    public String enterpriseEditView(@PathVariable UUID id) {
        return "enterprise_vehicle_list";
    }

    @GetMapping("/view/reports")
    public String reportsView() {
        return "reports";
    }
}

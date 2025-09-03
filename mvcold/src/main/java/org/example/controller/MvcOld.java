package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Brand;
import org.example.entity.Vehicle;
import org.example.service.BrandService;
import org.example.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MvcOld {
    private final BrandService brandService;
    private final VehicleService vehicleService;

    @GetMapping("/info")
    public String getAllVehicles(Model model) {
        List<Vehicle> allVehicles = vehicleService.getAllVehicles();
        List<Brand> allBrands = brandService.getAllBrands();
        model.addAttribute("vehicles", allVehicles);
        model.addAttribute("brands", allBrands);
        return "allInfo";
    }

    @PostMapping("/addBrand")
    public String addBrand(@ModelAttribute Brand brand) {
        brandService.save(brand);
        return "redirect:/info";
    }

    @PostMapping("/addVehicle")
    public String addVehicle(@ModelAttribute Vehicle vehicle, @RequestParam Long brandId) {
        Brand brand = brandService.findById(brandId);
        vehicle.setBrand(brand);
        vehicleService.save(vehicle);
        return "redirect:/info";
    }

    @GetMapping("/deleteBrand/{id}")
    public String deleteBrand(@PathVariable Long id) {
        vehicleService.deleteByBrandId(id);
        brandService.deleteById(id);
        return "redirect:/info";
    }

    @GetMapping("/editBrand/{id}")
    public String editBrandForm(@PathVariable Long id, Model model) {
        model.addAttribute("brand", brandService.findById(id));
        return "fragments/editBrandForm :: editBrandForm";
    }

    @GetMapping("/editVehicle/{id}")
    public String editVehicleForm(@PathVariable Long id, Model model) {
        model.addAttribute("vehicle", vehicleService.findById(id));
        model.addAttribute("brands", brandService.getAllBrands());
        return "fragments/editVehicleForm :: editVehicleForm";
    }

    @PostMapping("/updateBrand")
    public String updateBrand(@ModelAttribute Brand brand) {
        brandService.save(brand);
        return "redirect:/info";
    }

    @PostMapping("/updateVehicle")
    public String updateVehicle(@ModelAttribute Vehicle vehicle) {
        vehicleService.save(vehicle);
        return "redirect:/info";
    }
}

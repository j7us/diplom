package org.example.controller;

import org.example.entity.Brand;
import org.example.entity.Vehicle;
import org.example.service.BrandService;
import org.example.service.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MvcController {

    private final VehicleService vehicleService;
    private final BrandService brandService;

    public MvcController(VehicleService vehicleService, BrandService brandService) {
        this.vehicleService = vehicleService;
        this.brandService = brandService;
    }

    @GetMapping("/info")
    public String getAllVehicles(Model model) {
        List<Vehicle> allVehicles = vehicleService.getAllVehicles();
        List<Brand> allBrands = brandService.getAllBrands();
        model.addAttribute("vehicles", allVehicles);
        model.addAttribute("brands", allBrands);
        return "allInfo";
    }

    // Добавление нового бренда
    @PostMapping("/addBrand")
    public String addBrand(@ModelAttribute Brand brand) {
        brandService.save(brand);
        return "redirect:/info";
    }

    // Добавление нового транспортного средства
    @PostMapping("/addVehicle")
    public String addVehicle(@ModelAttribute Vehicle vehicle, @RequestParam Long brandId) {
        Brand brand = brandService.findById(brandId);
        vehicle.setBrand(brand);
        vehicleService.save(vehicle);
        return "redirect:/info";
    }

    // Удаление бренда
    @GetMapping("/deleteBrand/{id}")
    public String deleteBrand(@PathVariable Long id) {
        vehicleService.deleteByBrandId(id);
        brandService.deleteById(id);
        return "redirect:/info";
    }

    // Форма редактирования бренда (возвращает фрагмент для модального окна)
    @GetMapping("/editBrand/{id}")
    public String editBrandForm(@PathVariable Long id, Model model) {
        model.addAttribute("brand", brandService.findById(id));
        return "fragments/editBrandForm :: editBrandForm";
    }

    // Форма редактирования транспортного средства
    @GetMapping("/editVehicle/{id}")
    public String editVehicleForm(@PathVariable Long id, Model model) {
        model.addAttribute("vehicle", vehicleService.findById(id));
        model.addAttribute("brands", brandService.getAllBrands());
        return "fragments/editVehicleForm :: editVehicleForm";
    }

    // Обновление бренда
    @PostMapping("/updateBrand")
    public String updateBrand(@ModelAttribute Brand brand) {
        brandService.save(brand);
        return "redirect:/info";
    }

    // Обновление транспортного средства
    @PostMapping("/updateVehicle")
    public String updateVehicle(@ModelAttribute Vehicle vehicle) {
        vehicleService.save(vehicle);
        return "redirect:/info";
    }
}

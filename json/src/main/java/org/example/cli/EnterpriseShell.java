package org.example.cli;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.repository.DriverVehicleRepository;
import org.example.service.*;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static org.example.utils.VehicleUtils.getRandomCarNumber;
import static org.example.utils.VehicleUtils.getRandomDate;

@Command
@Component
@RequiredArgsConstructor
public class EnterpriseShell {
    private static AtomicInteger count = new AtomicInteger(0);

    private final VehicleService vehicleService;
    private final BrandService brandService;
    private final DriverService driverService;
    private final EnterpriseService enterpriseService;
    private final DriverVehicleRepository driverVehicleRepository;
    private final CoordinatesSender coordinatesSender;

    @Command(description = "add vehicle location points")
    public void addVehicleLocations(@Option UUID vehicleId) {
        coordinatesSender.sendCoordinates(vehicleId);
    }

    @Command(description = "Create vehicles for enterprises")
    public String createVehiclesForEnterprises(@Option Integer numberOfVehicles, @Option UUID[] enterprises) {
        List<Brand> allBrands = brandService.getAllBrands();
        Random random = new Random();

        for (UUID enterpriseId : enterprises) {
            createVehicles(
                    numberOfVehicles,
                    enterpriseService.getEntityById(enterpriseId),
                    allBrands,
                    random
            );
        }

        return "success";
    }

    private void createVehicles(Integer numberOfVehicles, Enterprise enterprise, List<Brand> brands, Random random) {
        for (int i = 0; i < numberOfVehicles; i++) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(UUID.randomUUID());
            vehicle.setBrand(brands.get(random.nextInt(brands.size())));
            vehicle.setEnterprise(enterprise);
            vehicle.setMileage(BigDecimal.valueOf(random.nextDouble()).setScale(2, RoundingMode.HALF_UP));
            vehicle.setPrice(BigDecimal.valueOf(random.nextDouble()).setScale(2, RoundingMode.HALF_UP));
            vehicle.setCarNumber(getRandomCarNumber());
            vehicle.setReleaseYear(getRandomDate());

            Vehicle saveVehicle = vehicleService.save(vehicle);

            int countCurrent = count.get();
            if (countCurrent % 10 == 0 && countCurrent != 0) {
                setDrivertoVehicle(saveVehicle, enterprise);
            }

            count.incrementAndGet();
        }
    }

    private void setDrivertoVehicle(Vehicle saveVehicle, Enterprise enterprise) {
        Driver driverWithRandom = createDriverWithRandom(enterprise);

        DriverVehicle driverVehicle = new DriverVehicle();
        driverVehicle.setDriver(driverWithRandom);
        driverVehicle.setVehicle(saveVehicle);
        driverVehicle.setActive(true);

        driverVehicleRepository.save(driverVehicle);
    }

    private Driver createDriverWithRandom(Enterprise enterprise) {
        Driver driver = new Driver();

        driver.setId(UUID.randomUUID());
        driver.setEnterprise(enterprise);
        driver.setName("Driver " + ThreadLocalRandom.current().nextInt(100));
        driver.setSalary(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble())
                .setScale(2, RoundingMode.HALF_UP));
        driver.setWorkExperience(BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(50))
                .setScale(2, RoundingMode.HALF_UP));

        return driverService.save(driver);
    }
}

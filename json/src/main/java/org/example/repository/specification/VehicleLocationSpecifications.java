package org.example.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.example.entity.Trip;
import org.example.entity.Vehicle;
import org.example.entity.VehicleLocation;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class VehicleLocationSpecifications {

    
    public static Specification<VehicleLocation> withinAnyTripDateRange(UUID vehicleId, List<Trip> trips) {
        return (root, query, criteriaBuilder) -> {
            
            Join<VehicleLocation, Vehicle> vehicleJoin = root.join("vehicle");
            
            Predicate vehiclePredicate = criteriaBuilder.equal(vehicleJoin.get("id"), vehicleId);
            
            List<Predicate> datePredicates = new ArrayList<>();
            for (Trip trip : trips) {
                Predicate dateBetween = criteriaBuilder.between(
                        root.get("date"), 
                        trip.getStartDate(), 
                        trip.getEndDate()
                );
                datePredicates.add(dateBetween);
            }
            
            Predicate dateRangePredicate = criteriaBuilder.or(datePredicates.toArray(new Predicate[0]));
            
            return criteriaBuilder.and(vehiclePredicate, dateRangePredicate);
        };
    }
}

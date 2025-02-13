package mirkoabozzi.Car_Catalog.specification;

import mirkoabozzi.Car_Catalog.entities.Car;
import mirkoabozzi.Car_Catalog.enums.VehicleStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class CarSpecification {

    public static Specification<Car> hasBrand(String brand) {
        return ((root, query, criteriaBuilder) -> {
            if (brand == null) return null;
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("brand")), brand.toLowerCase());
        });
    }

    public static Specification<Car> hasPriceRange(BigDecimal min, BigDecimal max) {
        return (root, query, criteriaBuilder) -> {
            if (min == null || max == null) return null;
            return criteriaBuilder.between(root.get("price"), min, max);
        };
    }

    public static Specification<Car> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return null;
            return criteriaBuilder.equal(root.get("vehicleStatus"), VehicleStatus.valueOf(status.toUpperCase()));
        };
    }
}

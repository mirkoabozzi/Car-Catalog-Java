package mirkoabozzi.Car_Catalog.repositories;

import mirkoabozzi.Car_Catalog.entities.Car;
import mirkoabozzi.Car_Catalog.enums.VehicleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {

    Page<Car> findByBrandIgnoreCase(Pageable pageable, String brand);

    @Query("SELECT c FROM Car c WHERE c.price BETWEEN :min AND :max")
    Page<Car> findByPriceRange(Pageable pageable, @Param("min") BigDecimal min, @Param("max") BigDecimal max);

    Page<Car> findByVehicleStatus(Pageable pageable, VehicleStatus vehicleStatus);
}

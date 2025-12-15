package mirkoabozzi.Car_Catalog.services;

import mirkoabozzi.Car_Catalog.dto.request.CarDTO;
import mirkoabozzi.Car_Catalog.dto.request.UpdateCarStatusDTO;
import mirkoabozzi.Car_Catalog.entities.Car;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.UUID;

public interface CarService {

    Car saveCar(CarDTO body);

    Page<Car> findAll(int page, int size, String sortBy);

    Page<Car> findByBrand(int page, int size, String sortBy, String brand);

    Page<Car> findByPriceRange(int page, int size, String sortBy, BigDecimal min, BigDecimal max);

    Page<Car> findCarsByStatus(int page, int size, String sortBy, String vehicleStatus);

    Car findById(UUID id);

    Car updateCar(UUID id, CarDTO body);

    Car updateCarStatus(UUID id, UpdateCarStatusDTO body);

    void deleteCar(UUID id);

    Page<Car> filterCar(int page, int size, String sortBy, String brand, BigDecimal min, BigDecimal max, String vehicleStatus);
}

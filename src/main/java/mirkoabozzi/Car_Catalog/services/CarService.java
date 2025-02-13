package mirkoabozzi.Car_Catalog.services;

import mirkoabozzi.Car_Catalog.dto.CarDTO;
import mirkoabozzi.Car_Catalog.entities.Car;
import mirkoabozzi.Car_Catalog.enums.VehicleStatus;
import mirkoabozzi.Car_Catalog.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public Car saveCar(CarDTO body) {

        Car newCar = new Car(
                body.brand(),
                body.model(),
                body.productionYear(),
                body.price(),
                VehicleStatus.valueOf(body.vehicleStatus().toUpperCase())
        );
        return this.carRepository.save(newCar);
    }

    public Page<Car> findAll(int page, int size, String sortBy) {
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.carRepository.findAll(pageable);
    }

    public Page<Car> findByBrand(int page, int size, String sortBy, String brand) {
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.carRepository.findByBrandIgnoreCase(pageable, brand);
    }

    public Page<Car> findByPriceRange(int page, int size, String sortBy, BigDecimal min, BigDecimal max) {
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.carRepository.findByPriceRange(pageable, min, max);
    }

    public Page<Car> findCarsByStatus(int page, int size, String sortBy, String vehicleStatus) {
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.carRepository.findByVehicleStatus(pageable, VehicleStatus.valueOf(vehicleStatus.toUpperCase()));
    }
}

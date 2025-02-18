package mirkoabozzi.Car_Catalog.services;

import mirkoabozzi.Car_Catalog.dto.request.CarDTO;
import mirkoabozzi.Car_Catalog.dto.request.UpdateCarStatusDTO;
import mirkoabozzi.Car_Catalog.entities.Car;
import mirkoabozzi.Car_Catalog.enums.VehicleStatus;
import mirkoabozzi.Car_Catalog.exceptions.NotFoundException;
import mirkoabozzi.Car_Catalog.repositories.CarRepository;
import mirkoabozzi.Car_Catalog.specification.CarSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

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

    private Car findById(UUID id) {
        return this.carRepository.findById(id).orElseThrow(() -> new NotFoundException("Car with id " + id + " not found on DB"));
    }

    public Car updateCar(UUID id, CarDTO body) {
        Car carFound = this.findById(id);

        carFound.setBrand(body.brand());
        carFound.setModel(body.model());
        carFound.setPrice(body.price());
        carFound.setProductionYear(body.productionYear());
        carFound.setVehicleStatus(VehicleStatus.valueOf(body.vehicleStatus().toUpperCase()));

        return this.carRepository.save(carFound);
    }

    public Car updateCarStatus(UUID id, UpdateCarStatusDTO body) {
        Car carFound = this.findById(id);
        carFound.setVehicleStatus(VehicleStatus.valueOf(body.vehicleStatus().toUpperCase()));
        return this.carRepository.save(carFound);
    }

    public void deleteCar(UUID id) {
        Car carFound = this.findById(id);
        carRepository.delete(carFound);
    }

    public Page<Car> filterCar(int page, int size, String sortBy, String brand, BigDecimal min, BigDecimal max, String vehicleStatus) {
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Specification<Car> specification = Specification.where(null);

        if (brand != null) specification = specification.and(CarSpecification.hasBrand(brand));
        if (min != null || max != null) specification = specification.and(CarSpecification.hasPriceRange(min, max));
        if (vehicleStatus != null) specification = specification.and(CarSpecification.hasStatus(vehicleStatus));

        return this.carRepository.findAll(specification, pageable);
    }
}

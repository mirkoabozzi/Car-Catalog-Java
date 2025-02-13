package mirkoabozzi.Car_Catalog.controllers;

import mirkoabozzi.Car_Catalog.dto.CarDTO;
import mirkoabozzi.Car_Catalog.dto.UpdateCarStatusDTO;
import mirkoabozzi.Car_Catalog.entities.Car;
import mirkoabozzi.Car_Catalog.exceptions.BadRequestException;
import mirkoabozzi.Car_Catalog.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Car save(@RequestBody @Validated CarDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            throw new BadRequestException("Body error: " + msg);
        } else {
            return this.carService.saveCar(body);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<Car> getAll(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "50") int size,
                            @RequestParam(defaultValue = "brand") String sortBy
    ) {
        return this.carService.findAll(page, size, sortBy);
    }

    @GetMapping("/brand")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<Car> findByBrand(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "50") int size,
                                 @RequestParam(defaultValue = "productionYear") String sortBy,
                                 @RequestParam String brand
    ) {
        return this.carService.findByBrand(page, size, sortBy, brand);
    }

    @GetMapping("/price")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<Car> getCarsByPriceRange(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "50") int size,
                                         @RequestParam(defaultValue = "price") String sortBy,
                                         @RequestParam(defaultValue = "0") BigDecimal min,
                                         @RequestParam BigDecimal max
    ) {
        return this.carService.findByPriceRange(page, size, sortBy, min, max);
    }

    @GetMapping("/status")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<Car> geCarsByStatus(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "50") int size,
                                    @RequestParam(defaultValue = "brand") String sortBy,
                                    @RequestParam(defaultValue = "AVAILABLE") String vehicleStatus
    ) {
        return this.carService.findCarsByStatus(page, size, sortBy, vehicleStatus);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Car updateCar(@PathVariable UUID id, @RequestBody @Validated CarDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            throw new BadRequestException("Body error: " + msg);
        } else {
            return this.carService.updateCar(id, body);
        }
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Car updateCarStatus(@PathVariable UUID id, @RequestBody @Validated UpdateCarStatusDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            throw new BadRequestException("Body error: " + msg);
        } else {
            return this.carService.updateCarStatus(id, body);
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable UUID id) {
        this.carService.deleteCar(id);
    }
}

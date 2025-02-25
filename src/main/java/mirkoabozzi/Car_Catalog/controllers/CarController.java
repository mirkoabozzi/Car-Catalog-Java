package mirkoabozzi.Car_Catalog.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mirkoabozzi.Car_Catalog.dto.request.CarDTO;
import mirkoabozzi.Car_Catalog.dto.request.UpdateCarStatusDTO;
import mirkoabozzi.Car_Catalog.dto.response.CarRespDTO;
import mirkoabozzi.Car_Catalog.entities.Car;
import mirkoabozzi.Car_Catalog.exceptions.BadRequestException;
import mirkoabozzi.Car_Catalog.services.CarService;
import org.modelmapper.ModelMapper;
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
@RequiredArgsConstructor
@RequestMapping("/api/cars")
@Tag(name = "Car")
public class CarController {

    private final CarService carService;
    private final ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CarRespDTO save(@RequestBody @Validated CarDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            throw new BadRequestException("Body error: " + msg);
        } else {
            Car car = this.carService.saveCar(body);
            return modelMapper.map(car, CarRespDTO.class);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<CarRespDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "50") int size,
                                   @RequestParam(defaultValue = "brand") String sortBy
    ) {
        Page<Car> carPage = this.carService.findAll(page, size, sortBy);
        return carPage.map(car -> modelMapper.map(car, CarRespDTO.class));

    }

    @GetMapping("/brand")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<CarRespDTO> findByBrand(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "50") int size,
                                        @RequestParam(defaultValue = "productionYear") String sortBy,
                                        @RequestParam String brand
    ) {
        Page<Car> carPage = this.carService.findByBrand(page, size, sortBy, brand);
        return carPage.map(car -> modelMapper.map(car, CarRespDTO.class));
    }

    @GetMapping("/price")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<CarRespDTO> getCarsByPriceRange(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "50") int size,
                                                @RequestParam(defaultValue = "price") String sortBy,
                                                @RequestParam(defaultValue = "0") BigDecimal min,
                                                @RequestParam BigDecimal max
    ) {
        Page<Car> carPage = this.carService.findByPriceRange(page, size, sortBy, min, max);
        return carPage.map(car -> modelMapper.map(car, CarRespDTO.class));
    }

    @GetMapping("/status")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<CarRespDTO> geCarsByStatus(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "50") int size,
                                           @RequestParam(defaultValue = "brand") String sortBy,
                                           @RequestParam(defaultValue = "AVAILABLE") String vehicleStatus
    ) {
        Page<Car> carPage = this.carService.findCarsByStatus(page, size, sortBy, vehicleStatus);
        return carPage.map(car -> modelMapper.map(car, CarRespDTO.class));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CarRespDTO updateCar(@PathVariable UUID id, @RequestBody @Validated CarDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            throw new BadRequestException("Body error: " + msg);
        } else {
            Car car = this.carService.updateCar(id, body);
            return this.modelMapper.map(car, CarRespDTO.class);
        }
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CarRespDTO updateCarStatus(@PathVariable UUID id, @RequestBody @Validated UpdateCarStatusDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            throw new BadRequestException("Body error: " + msg);
        } else {
            Car car = this.carService.updateCarStatus(id, body);
            return this.modelMapper.map(car, CarRespDTO.class);
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable UUID id) {
        this.carService.deleteCar(id);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<CarRespDTO> filterCars(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "50") int size,
                                       @RequestParam(defaultValue = "brand") String sortBy,
                                       @RequestParam(required = false) String brand,
                                       @RequestParam(required = false, defaultValue = "0") BigDecimal min,
                                       @RequestParam(required = false) BigDecimal max,
                                       @RequestParam(required = false) String vehicleStatus
    ) {
        Page<Car> carPage = this.carService.filterCar(page, size, sortBy, brand, min, max, vehicleStatus);
        return carPage.map(car -> modelMapper.map(car, CarRespDTO.class));
    }
}

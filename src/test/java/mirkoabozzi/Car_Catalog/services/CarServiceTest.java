package mirkoabozzi.Car_Catalog.services;

import mirkoabozzi.Car_Catalog.dto.request.CarDTO;
import mirkoabozzi.Car_Catalog.entities.Car;
import mirkoabozzi.Car_Catalog.enums.VehicleStatus;
import mirkoabozzi.Car_Catalog.mappers.CarMapper;
import mirkoabozzi.Car_Catalog.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    @Mock
    private CarRepository carRepository;
    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarService carService;

    private CarDTO carDTO;
    private Car car;
    private UUID id;

    @BeforeEach
    void setUp() {
        this.carDTO = new CarDTO("Alfa", "Mito", 2009, new BigDecimal(10000), String.valueOf(VehicleStatus.AVAILABLE));
        this.car = Car.builder()
                .brand("Alfa")
                .model("Mito")
                .productionYear(2009)
                .price(new BigDecimal(10000))
                .vehicleStatus(VehicleStatus.AVAILABLE)
                .build();
        this.id = UUID.randomUUID();
    }

    @Test
    void saveCar() {
        when(this.carRepository.save(any(Car.class))).thenReturn(car);
        when(carMapper.createCar(carDTO)).thenReturn(car);

        Car carSaved = this.carService.saveCar(carDTO);

        assertNotNull(carSaved);
        assertEquals(car.getBrand(), carSaved.getBrand());
        assertEquals(car.getModel(), carSaved.getModel());
    }

    @Test
    void updateCar() {
        Car updatedCar = Car.builder()
                .brand("Alfa")
                .model("Giulietta")
                .productionYear(2015)
                .price(new BigDecimal(15000))
                .vehicleStatus(VehicleStatus.SOLD)
                .build();
        when(this.carRepository.findById(id)).thenReturn(Optional.of(car));
        when(this.carRepository.save(any(Car.class))).thenReturn(updatedCar);

        CarDTO updateDTO = new CarDTO("Alfa", "Giulietta", 2015, new BigDecimal(15000), "SOLD");

        Car result = this.carService.updateCar(id, updateDTO);

        assertNotNull(result);
        assertEquals("Alfa", result.getBrand());
        assertEquals("Giulietta", result.getModel());
        assertEquals(VehicleStatus.SOLD, result.getVehicleStatus());
    }

    @Test
    void deleteCar() {
        when(this.carRepository.findById(id)).thenReturn(Optional.of(car));

        this.carService.deleteCar(id);

        verify(this.carRepository).delete(car);
    }
}
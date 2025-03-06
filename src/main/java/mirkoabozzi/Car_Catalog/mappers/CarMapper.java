package mirkoabozzi.Car_Catalog.mappers;

import lombok.RequiredArgsConstructor;
import mirkoabozzi.Car_Catalog.dto.request.CarDTO;
import mirkoabozzi.Car_Catalog.entities.Car;
import mirkoabozzi.Car_Catalog.enums.VehicleStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarMapper {

    public Car createCar(CarDTO body) {
        return Car.builder()
                .brand(body.brand())
                .model(body.model())
                .productionYear(body.productionYear())
                .price(body.price())
                .vehicleStatus(VehicleStatus.valueOf(body.vehicleStatus().toUpperCase()))
                .build();
    }
}

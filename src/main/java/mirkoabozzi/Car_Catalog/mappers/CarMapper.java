package mirkoabozzi.Car_Catalog.mappers;

import lombok.RequiredArgsConstructor;
import mirkoabozzi.Car_Catalog.dto.request.CarDTO;
import mirkoabozzi.Car_Catalog.dto.response.CarRespDTO;
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

    public CarRespDTO carToDTO(Car car) {
        return CarRespDTO.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .productionYear(car.getProductionYear())
                .price(car.getPrice())
                .vehicleStatus(String.valueOf(car.getVehicleStatus()))
                .build();
    }
}

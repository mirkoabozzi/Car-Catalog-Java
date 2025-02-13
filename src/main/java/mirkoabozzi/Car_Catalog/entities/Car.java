package mirkoabozzi.Car_Catalog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mirkoabozzi.Car_Catalog.enums.VehicleStatus;

import java.math.BigDecimal;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
public class Car extends Vehicle {

    public Car(String brand, String model, Integer productionYear, BigDecimal price, VehicleStatus vehicleStatus) {
        super(brand, model, productionYear, price, vehicleStatus);
    }
}

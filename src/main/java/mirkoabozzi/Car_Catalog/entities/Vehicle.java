package mirkoabozzi.Car_Catalog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mirkoabozzi.Car_Catalog.enums.VehicleStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class Vehicle {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @NotNull
    private UUID id;
    @NotNull
    private String brand;
    @NotNull
    private String model;
    @NotNull
    private Integer productionYear;
    @NotNull
    private BigDecimal price;
    @NotNull
    @Enumerated(EnumType.STRING)
    private VehicleStatus vehicleStatus;
}

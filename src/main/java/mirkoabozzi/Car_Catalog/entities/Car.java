package mirkoabozzi.Car_Catalog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Car extends Vehicle {
}

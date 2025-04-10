package mirkoabozzi.Car_Catalog.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarRespDTO {
    private UUID id;
    private String brand;
    private String model;
    private Integer productionYear;
    private BigDecimal price;
    private String vehicleStatus;
}

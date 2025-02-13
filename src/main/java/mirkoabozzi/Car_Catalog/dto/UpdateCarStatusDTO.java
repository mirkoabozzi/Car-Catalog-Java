package mirkoabozzi.Car_Catalog.dto;

import jakarta.validation.constraints.NotEmpty;

public record UpdateCarStatusDTO(
        @NotEmpty(message = "Vehicle status is required. ")
        String vehicleStatus
) {
}

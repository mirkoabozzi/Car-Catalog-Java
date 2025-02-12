package mirkoabozzi.Car_Catalog.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CarDTO(
        @NotEmpty(message = "Brand is required. ")
        String brand,
        @NotEmpty(message = "Model is required. ")
        String model,
        @NotNull(message = "Production year is required. ")
        @Min(value = 1900, message = "Invalid year. ")
        @Max(value = 2100, message = "Invalid year. ")
        Integer productionYear,
        @NotNull(message = "Price is required. ")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0. ")
        BigDecimal price,
        @NotEmpty(message = "Vehicle status is required. ")
        String vehicleStatus
) {
}
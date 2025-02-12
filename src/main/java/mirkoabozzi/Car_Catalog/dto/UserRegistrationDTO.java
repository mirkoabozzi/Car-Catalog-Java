package mirkoabozzi.Car_Catalog.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserRegistrationDTO(
        @NotEmpty(message = "Name is required. ")
        String name,
        @NotEmpty(message = "Email is required. ")
        String email,
        @NotEmpty(message = "Password is required. ")
        String password
) {
}

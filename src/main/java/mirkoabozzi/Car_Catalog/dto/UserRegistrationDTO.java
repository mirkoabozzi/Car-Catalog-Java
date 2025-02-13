package mirkoabozzi.Car_Catalog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRegistrationDTO(
        @NotEmpty(message = "Name is required. ")
        String name,
        @NotEmpty(message = "Surname is required. ")
        String surname,
        @NotEmpty(message = "Email is required. ")
        @Email(message = "Invalid email address. ")
        String email,
        @NotEmpty(message = "Password is required. ")
        String password
) {
}

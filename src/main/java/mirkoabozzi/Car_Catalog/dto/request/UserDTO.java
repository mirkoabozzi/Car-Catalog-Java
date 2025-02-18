package mirkoabozzi.Car_Catalog.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record UserDTO(
        @NotEmpty(message = "Name is required. ")
        String name,
        @NotEmpty(message = "Surname is required. ")
        String surname,
        @NotEmpty(message = "Email is required. ")
        String email
) {
}

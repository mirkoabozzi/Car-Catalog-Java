package mirkoabozzi.Car_Catalog.dto;

import jakarta.validation.constraints.NotEmpty;

public record UpdateUserRoleDTO(
        @NotEmpty(message = "User role is required. ")
        String userRole
) {
}

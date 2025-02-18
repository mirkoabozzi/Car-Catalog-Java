package mirkoabozzi.Car_Catalog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mirkoabozzi.Car_Catalog.enums.UserRole;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRespDTO {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private UserRole userRole;
    private Boolean isEnabled;
}

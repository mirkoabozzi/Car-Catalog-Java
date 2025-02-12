package mirkoabozzi.Car_Catalog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mirkoabozzi.Car_Catalog.enums.UserRole;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @NotNull
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private UserRole userRole;
    @NotNull
    private Boolean isEnabled;

    public User(String name, String email, String password, UserRole userRole, Boolean isEnabled) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.isEnabled = isEnabled;
    }
}

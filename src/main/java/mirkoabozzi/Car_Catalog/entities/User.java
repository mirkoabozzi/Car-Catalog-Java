package mirkoabozzi.Car_Catalog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import mirkoabozzi.Car_Catalog.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @NotNull
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole userRole;
    @NotNull
    private Boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.userRole.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}

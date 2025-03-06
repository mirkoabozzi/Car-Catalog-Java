package mirkoabozzi.Car_Catalog.mappers;

import lombok.RequiredArgsConstructor;
import mirkoabozzi.Car_Catalog.dto.request.UserRegistrationDTO;
import mirkoabozzi.Car_Catalog.entities.User;
import mirkoabozzi.Car_Catalog.enums.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User createUser(UserRegistrationDTO body) {
        return User.builder()
                .name(body.name())
                .surname(body.surname())
                .email(body.email())
                .password(passwordEncoder.encode(body.password()))
                .userRole(body.email().equals("mirko.abozzi@gmail.com") ? UserRole.ADMIN : UserRole.USER)
                .isEnabled(true)
                .build();
    }
}

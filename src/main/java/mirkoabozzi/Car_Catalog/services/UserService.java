package mirkoabozzi.Car_Catalog.services;

import mirkoabozzi.Car_Catalog.dto.UserRegistrationDTO;
import mirkoabozzi.Car_Catalog.entities.User;
import mirkoabozzi.Car_Catalog.enums.UserRole;
import mirkoabozzi.Car_Catalog.exceptions.BadRequestException;
import mirkoabozzi.Car_Catalog.exceptions.NotFoundException;
import mirkoabozzi.Car_Catalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found on DB"));
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email " + email + " not found on DB"));
    }

    public User seveUser(UserRegistrationDTO body) {
        if (this.userRepository.existsByEmail(body.email()))
            throw new BadRequestException("Email " + body.email() + " already on DB");

        User newUser = new User(
                body.name(),
                body.email(),
                this.passwordEncoder.encode(body.password()),
                (body.email().equals("mirko.abozzi@gmail.com") ? UserRole.ADMIN : UserRole.USER),
                true
        );
        return this.userRepository.save(newUser);
    }
}

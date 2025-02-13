package mirkoabozzi.Car_Catalog.services;

import mirkoabozzi.Car_Catalog.dto.UpdateUserRoleDTO;
import mirkoabozzi.Car_Catalog.dto.UserDTO;
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

    public User saveUser(UserRegistrationDTO body) {
        if (this.userRepository.existsByEmail(body.email()))
            throw new BadRequestException("Email " + body.email() + " already on DB");

        User newUser = new User(
                body.name(),
                body.surname(),
                body.email(),
                this.passwordEncoder.encode(body.password()),
                (body.email().equals("mirko.abozzi@gmail.com") ? UserRole.ADMIN : UserRole.USER),
                true
        );
        return this.userRepository.save(newUser);
    }

    public User updateUser(User authUser, UserDTO body) {
        if (!authUser.getEmail().equals(body.email()) && this.userRepository.existsByEmail(body.email()))
            throw new BadRequestException("Email " + body.email() + " already on DB");

        authUser.setName(body.name());
        authUser.setSurname(body.surname());
        authUser.setEmail(body.email());
        return this.userRepository.save(authUser);
    }

    public void deleteUser(UUID id) {
        User userFound = this.findById(id);
        this.userRepository.delete(userFound);
    }

    public User updateUserRole(UUID id, UpdateUserRoleDTO updateUserRoleDTO) {
        User userFound = this.findById(id);
        userFound.setUserRole(UserRole.valueOf(updateUserRoleDTO.userRole().toUpperCase()));
        return this.userRepository.save(userFound);
    }
}

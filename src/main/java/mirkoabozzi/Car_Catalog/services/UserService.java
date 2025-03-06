package mirkoabozzi.Car_Catalog.services;

import lombok.RequiredArgsConstructor;
import mirkoabozzi.Car_Catalog.dto.request.UpdateUserRoleDTO;
import mirkoabozzi.Car_Catalog.dto.request.UserDTO;
import mirkoabozzi.Car_Catalog.dto.request.UserRegistrationDTO;
import mirkoabozzi.Car_Catalog.entities.User;
import mirkoabozzi.Car_Catalog.enums.UserRole;
import mirkoabozzi.Car_Catalog.exceptions.BadRequestException;
import mirkoabozzi.Car_Catalog.exceptions.NotFoundException;
import mirkoabozzi.Car_Catalog.mappers.UserMapper;
import mirkoabozzi.Car_Catalog.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public User findById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found on DB"));
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email " + email + " not found on DB"));
    }

    public Page<User> findAll(int page, int size, String sortBy) {
        if (page > 100) page = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.userRepository.findAll(pageable);
    }

    public User saveUser(UserRegistrationDTO body) {
        if (this.userRepository.existsByEmail(body.email()))
            throw new BadRequestException("Email " + body.email() + " already on DB");
        User newUser = this.userMapper.createUser(body);
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

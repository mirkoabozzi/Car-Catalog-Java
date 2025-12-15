package mirkoabozzi.Car_Catalog.services;

import mirkoabozzi.Car_Catalog.dto.request.UpdateUserRoleDTO;
import mirkoabozzi.Car_Catalog.dto.request.UserDTO;
import mirkoabozzi.Car_Catalog.dto.request.UserRegistrationDTO;
import mirkoabozzi.Car_Catalog.entities.User;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {

    User findById(UUID id);

    User findByEmail(String email);

    Page<User> findAll(int page, int size, String sortBy);

    User saveUser(UserRegistrationDTO body);

    User updateUser(User authUser, UserDTO body);

    void deleteUser(UUID id);

    User updateUserRole(UUID id, UpdateUserRoleDTO updateUserRoleDTO);
}

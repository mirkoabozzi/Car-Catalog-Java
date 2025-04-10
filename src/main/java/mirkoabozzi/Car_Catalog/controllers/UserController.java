package mirkoabozzi.Car_Catalog.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mirkoabozzi.Car_Catalog.dto.request.UpdateUserRoleDTO;
import mirkoabozzi.Car_Catalog.dto.request.UserDTO;
import mirkoabozzi.Car_Catalog.dto.response.UserRespDTO;
import mirkoabozzi.Car_Catalog.entities.User;
import mirkoabozzi.Car_Catalog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/me")
    public UserRespDTO getMyProfile(@AuthenticationPrincipal User authUser) {
        return this.modelMapper.map(authUser, UserRespDTO.class);
    }

    @PutMapping("/me")
    public UserRespDTO updateMyProfile(@AuthenticationPrincipal User authUser, @RequestBody UserDTO body) {
        User user = this.userService.updateUser(authUser, body);
        return this.modelMapper.map(user, UserRespDTO.class);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<UserRespDTO> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "50") int size,
                                     @RequestParam(defaultValue = "surname") String sortBy
    ) {
        Page<User> userPage = this.userService.findAll(page, size, sortBy);
        return userPage.map(user -> modelMapper.map(user, UserRespDTO.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable UUID id) {
        this.userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserRespDTO updateRole(@PathVariable UUID id, @RequestBody @Validated UpdateUserRoleDTO body) {
        User user = this.userService.updateUserRole(id, body);
        return this.modelMapper.map(user, UserRespDTO.class);
    }
}
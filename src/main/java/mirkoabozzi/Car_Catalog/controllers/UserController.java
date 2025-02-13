package mirkoabozzi.Car_Catalog.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import mirkoabozzi.Car_Catalog.dto.UpdateUserRoleDTO;
import mirkoabozzi.Car_Catalog.dto.UserDTO;
import mirkoabozzi.Car_Catalog.entities.User;
import mirkoabozzi.Car_Catalog.exceptions.BadRequestException;
import mirkoabozzi.Car_Catalog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public User getMyProfile(@AuthenticationPrincipal User authUser) {
        return authUser;
    }

    @PutMapping("/me")
    public User updateMyProfile(@AuthenticationPrincipal User authUser, @RequestBody UserDTO body) {
        return this.userService.updateUser(authUser, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable UUID id) {
        this.userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateRole(@PathVariable UUID id, @RequestBody @Validated UpdateUserRoleDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            throw new BadRequestException("Body error: " + msg);
        } else {
            return this.userService.updateUserRole(id, body);
        }
    }
}
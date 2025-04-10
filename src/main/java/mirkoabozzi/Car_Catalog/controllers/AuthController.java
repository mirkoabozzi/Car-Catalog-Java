package mirkoabozzi.Car_Catalog.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mirkoabozzi.Car_Catalog.dto.request.UserLoginDTO;
import mirkoabozzi.Car_Catalog.dto.request.UserRegistrationDTO;
import mirkoabozzi.Car_Catalog.dto.response.UserLoginRespDTO;
import mirkoabozzi.Car_Catalog.dto.response.UserRespDTO;
import mirkoabozzi.Car_Catalog.entities.User;
import mirkoabozzi.Car_Catalog.services.AuthService;
import mirkoabozzi.Car_Catalog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authorization", description = "Public endpoint")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRespDTO saveUser(@RequestBody @Validated UserRegistrationDTO body) {
        User user = this.userService.saveUser(body);
        return this.modelMapper.map(user, UserRespDTO.class);
    }

    @GetMapping("/login")
    public UserLoginRespDTO login(@RequestBody @Validated UserLoginDTO body) {
        return new UserLoginRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }
}

package mirkoabozzi.Car_Catalog.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import mirkoabozzi.Car_Catalog.dto.request.UserLoginDTO;
import mirkoabozzi.Car_Catalog.dto.request.UserRegistrationDTO;
import mirkoabozzi.Car_Catalog.dto.response.UserLoginRespDTO;
import mirkoabozzi.Car_Catalog.dto.response.UserRespDTO;
import mirkoabozzi.Car_Catalog.entities.User;
import mirkoabozzi.Car_Catalog.exceptions.BadRequestException;
import mirkoabozzi.Car_Catalog.services.AuthService;
import mirkoabozzi.Car_Catalog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authorization", description = "Public endpoint")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRespDTO saveUser(@RequestBody @Validated UserRegistrationDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            throw new BadRequestException("Body error: " + msg);
        } else {
            User user = this.userService.saveUser(body);
            return this.modelMapper.map(user, UserRespDTO.class);
        }
    }

    @GetMapping("/login")
    public UserLoginRespDTO login(@RequestBody @Validated UserLoginDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String msg = validation.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
            throw new BadRequestException("Body error: " + msg);
        } else {
            return new UserLoginRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
        }

    }

}

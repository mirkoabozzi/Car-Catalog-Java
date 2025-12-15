package mirkoabozzi.Car_Catalog.services.impl;

import lombok.RequiredArgsConstructor;
import mirkoabozzi.Car_Catalog.dto.request.UserLoginDTO;
import mirkoabozzi.Car_Catalog.entities.User;
import mirkoabozzi.Car_Catalog.exceptions.UnauthorizedException;
import mirkoabozzi.Car_Catalog.security.JWTTools;
import mirkoabozzi.Car_Catalog.services.AuthService;
import mirkoabozzi.Car_Catalog.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTTools jwtTools;

    @Override
    public String checkCredentialsAndGenerateToken(UserLoginDTO body) {

        User userFound = this.userService.findByEmail(body.email());
        if (!this.passwordEncoder.matches(body.password(), userFound.getPassword()))
            throw new UnauthorizedException("Incorrect password");
        if (!userFound.getIsEnabled())
            throw new UnauthorizedException("Account disabled");
        else return this.jwtTools.generateToken(userFound);
    }
}
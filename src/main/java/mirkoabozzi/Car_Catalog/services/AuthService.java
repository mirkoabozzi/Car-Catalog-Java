package mirkoabozzi.Car_Catalog.services;

import mirkoabozzi.Car_Catalog.dto.UserLoginDTO;
import mirkoabozzi.Car_Catalog.entities.User;
import mirkoabozzi.Car_Catalog.exceptions.UnauthorizedException;
import mirkoabozzi.Car_Catalog.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTTools jwtTools;

    public String checkCredentialsAndGenerateToken(UserLoginDTO body) {

        User userFound = this.userService.findByEmail(body.email());
        if (!this.passwordEncoder.matches(body.password(), userFound.getPassword()))
            throw new UnauthorizedException("Incorrect password");
        if (!userFound.getIsEnabled())
            throw new UnauthorizedException("Account disabled");
        else return this.jwtTools.generateToken(userFound);
    }
}
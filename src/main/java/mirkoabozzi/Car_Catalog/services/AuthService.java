package mirkoabozzi.Car_Catalog.services;

import mirkoabozzi.Car_Catalog.dto.request.UserLoginDTO;

public interface AuthService {

    String checkCredentialsAndGenerateToken(UserLoginDTO body);
}

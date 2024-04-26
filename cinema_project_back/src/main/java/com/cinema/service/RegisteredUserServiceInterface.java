package com.cinema.service;

import com.cinema.bodies.LoginDataObject;
import com.cinema.bodies.UpdateUserDataObject;
import com.cinema.model.RegisteredUser;
import com.cinema.bodies.RegistrationDataObject;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface RegisteredUserServiceInterface extends UserDetailsService {

    public Boolean updateUserData(UpdateUserDataObject userData);
    public Boolean save(RegistrationDataObject user);

    public Boolean saveAdmin(RegistrationDataObject user);

    public String getLoginToken(LoginDataObject loginDataObject);

    public String getRefreshToken(LoginDataObject loginDataObject);

    public String getLoginToken(String jwtRefreshToken);

    public String getRefreshToken(String jwtRefreshToken);

    public void deleteRefreshToken(String refreshToken);

    public RegisteredUser getUserInfo(String jwtToken);

    public Boolean deleteUser(Long id);

    public List<RegisteredUser> findAll();

    public Boolean loginAvailable(String login);

    public Boolean mailAvailable(String mail);
}

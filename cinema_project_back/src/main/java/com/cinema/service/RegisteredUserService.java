package com.cinema.service;

import com.cinema.bodies.LoginDataObject;
import com.cinema.bodies.RegistrationDataObject;
import com.cinema.bodies.UpdateUserDataObject;
import com.cinema.model.*;
import com.cinema.repository.RefreshTokenRepository;
import com.cinema.repository.RegisteredUserRepository;
import com.cinema.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for registered users endpoints.
 */
@Service
public class RegisteredUserService implements  RegisteredUserServiceInterface{

    private RegisteredUserRepository registeredUserRepository;
    private RefreshTokenRepository refreshTokenRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtServiceInterface jwtService;

    @Autowired
    public RegisteredUserService(RegisteredUserRepository registeredUserRepository, RefreshTokenRepository refreshTokenRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtServiceInterface jwtService) {
        this.registeredUserRepository = registeredUserRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public List<RegisteredUser> findAll() {
        List<RegisteredUser> users = registeredUserRepository.findAll();
        Role admin_role = new Role("ADMIN");
        users = users.stream().filter( user -> !user.getRoles().contains(admin_role)).toList();
        return users;
    }

    @Override
    @Transactional
    public Boolean save(RegistrationDataObject user) {
        RegisteredUser registeredUser = new RegisteredUser();
        Role role = roleRepository.findRolesByRole("USER");

        registeredUser.setLogin( user.getLogin() );
        registeredUser.setFname( user.getFname() );
        registeredUser.setLname( user.getLname() );
        registeredUser.setMail(  user.getMail() );
        registeredUser.setPassword( passwordEncoder.encode(user.getPassword()) );
        registeredUser.setPhone( user.getPhone() );
        registeredUser.setRoles( List.of( role ) );
        registeredUserRepository.save( registeredUser );
        return true;
    }

    @Override
    @Transactional
    public Boolean saveAdmin(RegistrationDataObject user) {
        RegisteredUser registeredUser = new RegisteredUser();
        Role role1 = roleRepository.findRolesByRole("ADMIN");
        Role role2 = roleRepository.findRolesByRole("USER");

        registeredUser.setLogin( user.getLogin() );
        registeredUser.setFname( user.getFname() );
        registeredUser.setLname( user.getLname() );
        registeredUser.setMail(  user.getMail() );
        registeredUser.setPassword( passwordEncoder.encode(user.getPassword()) );
        registeredUser.setPhone( user.getPhone() );
        registeredUser.setRoles( List.of( role1, role2 ) );
        registeredUserRepository.save( registeredUser );
        return true;
    }


    @Override
    public String getLoginToken(LoginDataObject loginDataObject) {
        String login = loginDataObject.getLogin();
        String pass = loginDataObject.getPassword();

        RegisteredUser user = registeredUserRepository.findRegisteredUserByLogin(login).orElseThrow();

        if(passwordEncoder.matches(pass, user.getPassword()) ) {
            return jwtService.generateToken(user);
        } else {
            return "Credentials-Not-Valid";
        }
    }

    @Override
    public String getLoginToken(String jwtRefreshToken) {
        RefreshToken oldRefreshToken = refreshTokenRepository.findRefreshTokenByToken(jwtRefreshToken);
        RegisteredUser user = oldRefreshToken.getRegisteredUser();
        System.out.println("Old founded token: " + oldRefreshToken.toString());
        System.out.println("User found:        " + user.toString() );

        return jwtService.generateToken(user);
    }

    @Override
    public String getRefreshToken(LoginDataObject loginDataObject) {
        RegisteredUser user = registeredUserRepository.findRegisteredUserByLogin( loginDataObject.getLogin() ).orElseThrow();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken( UUID.randomUUID().toString() );
        refreshToken.setRegisteredUser( user );
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    @Override
    @Transactional
    public String getRefreshToken(String jwtRefreshedToken) {
        RefreshToken oldRefreshedToken = refreshTokenRepository.findRefreshTokenByToken(jwtRefreshedToken);
        RefreshToken newRefreshedToken = new RefreshToken();
        RegisteredUser user = oldRefreshedToken.getRegisteredUser();

        System.out.println("Searching by rtoken: " + jwtRefreshedToken);
        System.out.println("Old founded token: " + oldRefreshedToken.toString());
        refreshTokenRepository.deleteByToken( oldRefreshedToken.getToken() );
        newRefreshedToken.setRegisteredUser(user);
        newRefreshedToken.setToken( UUID.randomUUID().toString() );
        refreshTokenRepository.save(newRefreshedToken);

        return newRefreshedToken.getToken();
    }

    @Override
    @Transactional
    public void deleteRefreshToken(String jwtRefreshToken) {
        RefreshToken oldRefreshedToken = refreshTokenRepository.findRefreshTokenByToken(jwtRefreshToken);
        refreshTokenRepository.deleteByToken( oldRefreshedToken.getToken() );
    }

    @Override
    public RegisteredUser getUserInfo(String jwtToken) {
        String login = jwtService.extractUsername(jwtToken);
        Optional<RegisteredUser> user = registeredUserRepository.findRegisteredUserByLogin(login);
        System.out.println("Sending out userInfo");
        return user.orElseThrow( () -> new UsernameNotFoundException("No available details for this user") );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Looking for user : " + username);
        Optional<RegisteredUser> user = registeredUserRepository.findRegisteredUserByLogin(username);
        System.out.println("Found : " + user);
        return user.orElseThrow( () -> new UsernameNotFoundException("Auth Error") );
    }

    @Override
    public Boolean deleteUser(Long id) {
        Optional<RegisteredUser> user = registeredUserRepository.findById(id);
        if( user.isPresent() ) {
            registeredUserRepository.delete(user.get());
            return true;
        }
        return false;
    }


    @Override
    public Boolean loginAvailable(String login) {
        Optional<RegisteredUser> maybe_user = registeredUserRepository.findRegisteredUserByLogin(login);
        System.out.println( "Found user? : " + maybe_user.isPresent() );
        return maybe_user.isEmpty();
    }

    @Override
    public Boolean mailAvailable(String mail) {
        Optional<RegisteredUser> maybe_user = registeredUserRepository.findRegisteredUserByMail(mail);
        return maybe_user.isEmpty();
    }

    @Transactional
    public Boolean updateUserData(UpdateUserDataObject userData) {
        Boolean success = false;
        Optional<RegisteredUser> user = registeredUserRepository.findRegisteredUserByLogin( userData.getLogin() );
        RegistrationDataObject userDataToUpdate;

        if(user.isPresent()) {
            userDataToUpdate = userData.getDataToUpdate();
            System.out.println("Altering user: " + userDataToUpdate.toString());
            success = true;
        }
        return success;
    }
}

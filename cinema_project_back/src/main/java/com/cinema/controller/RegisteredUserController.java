package com.cinema.controller;


import com.cinema.bodies.BasicUserInfo;
import com.cinema.bodies.LoginDataObject;
import com.cinema.bodies.RegistrationDataObject;
import com.cinema.bodies.UpdateUserDataObject;
import com.cinema.model.*;
import com.cinema.service.JwtServiceInterface;
import com.cinema.service.RegisteredUserServiceInterface;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.util.List;


/**
 * It contains endpoints regarding auth and user data exchange.
 */
@RestController
@RequestMapping("/user")
public class RegisteredUserController {


    /**
     * Represents user service layer.
     */
    private RegisteredUserServiceInterface registeredUserService;

    /**
     * Represents JWT token service layer.
     */
    private JwtServiceInterface jwtService;

    @Autowired
    public RegisteredUserController(RegisteredUserServiceInterface registeredUserService, JwtServiceInterface jwtService) {
        this.registeredUserService = registeredUserService;
        this.jwtService = jwtService;
    }

    /**
     * @return List of registered users excluding ones with admin role.
     */
    @GetMapping("/registered")
    public List<RegisteredUser> getAllRegUsers() {
        return registeredUserService.findAll();
    }

    /**
     * @param request It is get request without parameters.
     * @return User instance based on credentials in request.
     */
    @GetMapping("/info")
    public ResponseEntity<RegisteredUser> getRegisteredUserFromToken( HttpServletRequest request ) {
        String jwtToken = WebUtils.getCookie(request, "jwt-token").getValue();
        RegisteredUser user = registeredUserService.getUserInfo(jwtToken);
        return ResponseEntity.ok(user);
    }

    /**
     *
     * @param registrationUserData It is body from post request representing essential data for user registration.
     * @return true after registration.
     */
    @PostMapping("/register")
    public ResponseEntity<Boolean> registerUserToDatabase(@RequestBody RegistrationDataObject registrationUserData ) {
        registeredUserService.save(registrationUserData);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/register/admin")
    public ResponseEntity<Boolean> registerAdminToDatabase() {
        RegistrationDataObject registrationDataObject = new RegistrationDataObject();
        registrationDataObject.setLogin("admin");
        registrationDataObject.setFname("Marian");
        registrationDataObject.setLname("Kowalski");
        registrationDataObject.setPassword("admin");
        registrationDataObject.setPhone("543123321");
        registrationDataObject.setMail("admin@mail.com");
        registeredUserService.saveAdmin(registrationDataObject);
        return ResponseEntity.ok(true);
    }


//    /**
//     * @param registrationDataObject
//     * @return
//     */
//    @PostMapping("/registerAdmin")
//    public Boolean registerAdmin( @RequestBody RegistrationDataObject registrationDataObject ) {
//        registeredUserService.saveAdmin(registrationDataObject);
//        return true;
//    }

    /**
     * Function creates tokens and places them in request httpOnlyCookies.
     * @param loginUserFormData It represents login form data sent in post request.
     * @return response with BasicUserInfo and valid credentials after successful user validation.
     */
    @PostMapping("/login")
    public ResponseEntity<BasicUserInfo> logUser(@RequestBody LoginDataObject loginUserFormData) {

        String jwtToken = registeredUserService.getLoginToken(loginUserFormData);
        String jwtRefreshToken = "Credentials-Not-Valid";
        BasicUserInfo userInfo = new BasicUserInfo();

        if( !jwtToken.equals("Credentials-Not-Valid")) {
            jwtRefreshToken = registeredUserService.getRefreshToken(loginUserFormData);
            UserDetails userDetails = registeredUserService.loadUserByUsername( loginUserFormData.getLogin() );
            userInfo.setLogin( userDetails.getUsername() );
            userInfo.setRoles( userDetails.getAuthorities().stream().map( GrantedAuthority::getAuthority ).toList() );
        }

        ResponseCookie jwtTokenCookie = ResponseCookie.from("jwt-token", jwtToken).path("/").httpOnly(true).build();
        ResponseCookie jwtRefreshTokenCookie = ResponseCookie.from("jwt-token-refresh", jwtRefreshToken).path("/").httpOnly(true).build();

        return ResponseEntity
                .ok()
                .header( HttpHeaders.SET_COOKIE, jwtTokenCookie.toString() )
                .header( HttpHeaders.SET_COOKIE, jwtRefreshTokenCookie.toString() )
                .body(userInfo);
    }


    /**
     * Function refreshes token by using refresh token in request.
     * @param request It is post request with expired credential.
     * @return response with message "Token refreshed" and refreshed credentials in httpOnlyCookies if succeeded.
     */
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(HttpServletRequest request) {
        String jwtToken = WebUtils.getCookie(request, "jwt-token").getValue();
        String jwtRefreshToken = WebUtils.getCookie(request, "jwt-token-refresh").getValue();

//        System.out.println("token:  " + jwtToken);
//        System.out.println("Rtoken: " + jwtRefreshToken);

        if( jwtService.isTokenExpiredValidation(jwtToken) ) {
            jwtToken = registeredUserService.getLoginToken(jwtRefreshToken);
            jwtRefreshToken = registeredUserService.getRefreshToken(jwtRefreshToken);

//            System.out.println("New token:  " + jwtToken);
//            System.out.println("New Rtoken: " + jwtRefreshToken);
        }

        ResponseCookie jwtTokenCookie = ResponseCookie.from("jwt-token", jwtToken).path("/").maxAge(60*60*24).httpOnly(true).build();
        ResponseCookie jwtRefreshTokenCookie = ResponseCookie.from("jwt-token-refresh", jwtRefreshToken).path("/").maxAge(60*60*24).httpOnly(true).build();

        return ResponseEntity
                .ok()
                .header( HttpHeaders.SET_COOKIE, jwtTokenCookie.toString() )
                .header( HttpHeaders.SET_COOKIE, jwtRefreshTokenCookie.toString() )
                .body("Token Refreshed");
    }


    /**
     * Function logs out user by deleting refresh token from database and replacing cookies in request.
     * @param request It represents empty post request.
     * @return true
     */
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(HttpServletRequest request) {

        try {
            String jwtRefreshToken = WebUtils.getCookie(request, "jwt-token-refresh").getValue();
            registeredUserService.deleteRefreshToken(jwtRefreshToken);
        } catch (Exception e) {
            System.out.println("Problem with finding user or getting token from cookie");
        }

        ResponseCookie jwtEmptyTokenCookie = ResponseCookie.from("jwt-token", "clean").path("/").httpOnly(true).maxAge(1).build();
        ResponseCookie jwtEmptyRefreshTokenCookie = ResponseCookie.from("jwt-token-refresh", "clean").path("/").httpOnly(true).maxAge(1).build();

        return ResponseEntity
                .ok()
                .header( HttpHeaders.SET_COOKIE, jwtEmptyTokenCookie.toString() )
                .header( HttpHeaders.SET_COOKIE, jwtEmptyRefreshTokenCookie.toString() )
                .body(true);
    }


    /**
     * Endpoint deleting registered user from database
     * @param id It represents user id.
     * @return true if succeeded and false otherwise.
     */
    @GetMapping("/delete/{user_id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("user_id") Long id) {
        Boolean success = registeredUserService.deleteUser(id);
        return ResponseEntity.ok(success);
    }

    /**
     * Endpoint for checking if user login is available
     * @param req_body String value from post request body with name req_body.
     * @return true if login is available and false otherwise.
     */
    @PostMapping("/available/login")
    public ResponseEntity<Boolean> loginAvailable(@RequestBody String req_body) {
        JSONObject login_j = new JSONObject(req_body);
        String login = login_j.getString("login");
        Boolean success = registeredUserService.loginAvailable(login);
        return ResponseEntity.ok(success);
    }

    /**
     * Enpoint for checking if user mail is available.
     * @param req_body Strgin value from post request body with name req_body.
     * @return true if mail is available and false otherwise.
     */
    @PostMapping("/available/mail")
    public ResponseEntity<Boolean> mailAvailable(@RequestBody String req_body) {
        JSONObject mail_j = new JSONObject(req_body);
        String mail = mail_j.getString("mail");
        Boolean success = registeredUserService.mailAvailable(mail);
        return ResponseEntity.ok(success);
    }


    @PostMapping("/update")
    public ResponseEntity<Boolean> updateUserData(@RequestBody UpdateUserDataObject userData) {
        Boolean success = registeredUserService.updateUserData(userData);
        return ResponseEntity.ok(success);
    }
}

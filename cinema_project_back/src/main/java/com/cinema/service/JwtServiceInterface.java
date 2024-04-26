package com.cinema.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

public interface JwtServiceInterface {
    public Key getSignInKey();

    public String generateToken(UserDetails userDetails);

    public String extractUsername( String jwtToken );

    public Date extractExpiration(String jwtToken );
    public Boolean isTokenExpired( String jwtToken );

    public Boolean isTokenExpiredValidation( String jwtToken );
    public Boolean isTokenValid( String jwtToken, UserDetails userDetails );

}

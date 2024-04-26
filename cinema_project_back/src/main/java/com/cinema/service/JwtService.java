package com.cinema.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/**
 * Service generating deserializing and refreshing tokens.
 */
@Service
public class JwtService implements JwtServiceInterface{

    private static final String SECRET_KEY = "51655468576D5A7134743677397A24432646294A404E635266556A586E327235";

    public JwtService() {
    }

    @Override
    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt( new Date(System.currentTimeMillis()) )
                .setExpiration( new Date(System.currentTimeMillis() + 1000 * 60 * 1000)) // 1min
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    @Override
    public String extractUsername( String jwtToken ) {
        return Jwts.parserBuilder()
                .setSigningKey( getSignInKey() )
                .build()
                .parseClaimsJws( jwtToken )
                .getBody()
                .getSubject();
    }

    @Override
    public Date extractExpiration( String jwtToken ) {
        return Jwts.parserBuilder()
                .setSigningKey( getSignInKey() )
                .build()
                .parseClaimsJws( jwtToken )
                .getBody()
                .getExpiration();
    }

    @Override
    public Boolean isTokenExpired( String jwtToken ) {
        return extractExpiration(jwtToken).before( new Date() );
    }

    @Override
    public Boolean isTokenValid( String jwtToken, UserDetails userDetails ) {
        return !isTokenExpiredValidation(jwtToken) && userDetails.getUsername().equals( extractUsername(jwtToken) );
    }

    @Override
    public Boolean isTokenExpiredValidation(String jwtToken) {
        try {
            return extractExpiration(jwtToken).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
}

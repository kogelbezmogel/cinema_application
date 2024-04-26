package com.cinema.config;

import com.cinema.service.JwtServiceInterface;
import com.cinema.service.RegisteredUserServiceInterface;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

/**
 * Custom filter with JWT token authentication.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";

    final private RegisteredUserServiceInterface registerUserServiceInterface;

    final private JwtServiceInterface jwtService;

    @Autowired
    public JwtAuthFilter(RegisteredUserServiceInterface registerUserServiceInterface, JwtServiceInterface jwtService) {
        this.registerUserServiceInterface = registerUserServiceInterface;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken;
        String userLogin;

        try { // check if cookie exists
            jwtToken = WebUtils.getCookie(request, "jwt-token").getValue();
        } catch (NullPointerException e) {
            filterChain.doFilter(request, response);
            return;
        }

        try { //check for expiration
            jwtService.isTokenExpired(jwtToken);
        } catch (ExpiredJwtException e) {
            filterChain.doFilter(request, response);
            return;
        } catch (MalformedJwtException e) {
            filterChain.doFilter(request, response);
            return;
        }

        userLogin = jwtService.extractUsername( jwtToken );

        if( userLogin != null && SecurityContextHolder.getContext().getAuthentication() == null ) {// check if there is login token and if request is not already authenticated
            UserDetails userDetails = this.registerUserServiceInterface.loadUserByUsername(userLogin);

            if( jwtService.isTokenValid(jwtToken, userDetails) ) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
                usernamePasswordAuthenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request) );
                SecurityContextHolder.getContext().setAuthentication( usernamePasswordAuthenticationToken );
            }
            filterChain.doFilter(request, response);
        }
    }

}

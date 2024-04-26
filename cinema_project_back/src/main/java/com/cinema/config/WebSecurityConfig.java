package com.cinema.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * It contains security configuration
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration {

    /**
     * Represents filter for credentials authentication
     */
    final private JwtAuthFilter jwtAuthFilter;

    @Autowired
    public WebSecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * This function represents filter chain through which every request is passed.
     * @param http It is configuration to be build for request.
     * @return created configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors() //adds usage of corsConfiguration beans
                .and()
                .csrf( AbstractHttpConfigurer::disable ) //instead fo el -> el.method => class::method
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers(
                            "/hello",
                            "/user/login",
                            "/user/register",
                            "/user/register/admin",
                            "/user/logout",
                            "/user/refresh",
                            "/user/available/**",
                            "/movie/range",
                            "/sit/show/**",
                            "/room/show/**",
                            "/ticket/buy",
                            "/event/all",
                            "/show/**" // this needs to be changed
                    ).permitAll();
                    auth.anyRequest().authenticated();
                });

        http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return  http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }


}

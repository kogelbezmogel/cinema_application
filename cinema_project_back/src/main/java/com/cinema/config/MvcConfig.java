package com.cinema.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;


@EnableWebMvc
@Configuration
@ComponentScan({"com.cinema"})
public class MvcConfig implements WebMvcConfigurer {

}

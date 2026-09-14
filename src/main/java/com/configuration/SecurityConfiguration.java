package com.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {


        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(req -> req.anyRequest().permitAll());


        return http.build();
    }
}

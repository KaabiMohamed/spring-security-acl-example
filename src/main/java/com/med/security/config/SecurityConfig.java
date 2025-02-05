package com.med.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for the H2 console
                .headers().frameOptions().disable() // Allow frames for H2 console
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/h2-console/**").permitAll() // Allow unrestricted access to H2 Console
                .requestMatchers("/message").permitAll() // Allow unrestricted access to H2 Console
                .requestMatchers("/message/**").permitAll() // Allow unrestricted access to H2 Console
                .anyRequest().authenticated(); // Restrict all other endpoints

        return http.build();
    }
}

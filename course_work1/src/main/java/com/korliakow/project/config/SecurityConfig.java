package com.korliakow.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Stream;

@Configuration
public class SecurityConfig {

    public static final List<String> PERMIT_ALL_API_ENDPOINTS = Stream.of(
            "/**"
    ).toList();

    private static final int BCRYPT_STRENGTH = 11;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                PERMIT_ALL_API_ENDPOINTS.toArray(String[]::new)
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1" + "/**").permitAll()
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_STRENGTH);
    }

}

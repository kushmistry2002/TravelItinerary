package com.kush.travel_api.config;

import com.kush.travel_api.exception.AuthException;
import com.kush.travel_api.exception.GlobalExceptionHandler;
import com.kush.travel_api.security.JwtAuthEntryPoint;
import com.kush.travel_api.security.JwtAuthFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    // Create JwtAuthFilter bean
    @Bean
    public JwtAuthFilter jwtAuthFilter(){
        return new JwtAuthFilter();
    }

    // Create PasswordEncoder bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Create SecurityFilterChain bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CORS
        http.cors(cors->cors.disable());

        // Disable CSRF
        http.csrf(csrf->csrf.disable());

        // Change session management to STATELESS
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add exception handler
        http.exceptionHandling(ex->ex.authenticationEntryPoint(jwtAuthEntryPoint));       

        // Authorize http requests
        http.authorizeHttpRequests(auth->auth.requestMatchers("/").permitAll()
                .requestMatchers("/app-auth/token/**").permitAll()
                .requestMatchers("/app-auth/createUser/**").permitAll()
                .anyRequest().authenticated());

        // Add JWT authentication filter
        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

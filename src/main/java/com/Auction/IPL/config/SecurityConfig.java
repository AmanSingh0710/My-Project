package com.Auction.IPL.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean for UserDetailsService with in-memory users
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
           UserDetails adminUser = User.builder()
                   .username("admin")
                   .password(encoder.encode("admin123"))
                   .roles("ADMIN")
                   .build();
   
           UserDetails normalUser = User.builder()
                   .username("user")
                   .password(encoder.encode("user123"))
                   .roles("USER")
                   .build();
   
           return new InMemoryUserDetailsManager(adminUser, normalUser);
    }


    // Bean for AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    // Bean for SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF for API security
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()  // Allow public authentication endpoints
                .requestMatchers("/teams/**").hasRole("ADMIN")
                .anyRequest().authenticated()  // Secure all other endpoints
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No sessions (stateless API)
                .httpBasic();

         return http.build(); // Enable Basic Authentication
    }
}

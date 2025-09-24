package com.message.Config;

import com.message.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JWTFilter jwtFilter;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private String[] OPEN_URLS = {
            "/api/v1/message/register",
            "/api/v1/message/login",
        "/",
        "/index.html",
        "/css/**",
        "/js/**",
        "/images/**",
        "/html/**"
        
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(OPEN_URLS).permitAll()

                        // Admin-only page
                        .requestMatchers("/admin.html").hasRole("ADMIN")

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



//------------spring security username and password verification-------------------

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

@Bean

    public AuthenticationProvider authProvider(){

    DaoAuthenticationProvider Dao = new DaoAuthenticationProvider();
   Dao.setUserDetailsService(customUserDetailsService);
    Dao.setPasswordEncoder(passwordEncoder());
    return Dao;

}














}

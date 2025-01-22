package com.drvservicios.universidadFront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Deshabilitar CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/css/**", "/img/**", "/js/**").permitAll()  // Permitir acceso sin autenticación a estas rutas
                .anyRequest().authenticated()  // Rutas protegidas requieren autenticación
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
            );
        return http.build();
    }
}

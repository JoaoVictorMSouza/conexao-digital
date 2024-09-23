package com.conexao_digital.frontoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SegurancaConfig {
    // Removendo login do Spring Security
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers("/", "/home", "/public/**").permitAll() // Permite acesso público a estas URLs
    //             .anyRequest().authenticated() // Requer autenticação para qualquer outra requisição
    //         )
    //         .formLogin(form -> form
    //             .loginPage("/login") // Define uma página de login personalizada
    //             .permitAll()
    //         )
    //         .logout(logout -> logout
    //             .permitAll()
    //         );
    //     return http.build();
    // }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite todas as requisições sem autenticação
            )
            .csrf(CsrfConfigurer::disable)
            .headers(headers -> headers
                .addHeaderWriter((request, response) -> {
                    response.setHeader("X-Frame-Options", "ALLOW-FROM http://localhost:8080"); // Permite o uso de frames no H2 Console
                })
            );
        return http.build();
    }
    @Bean
    public PasswordEncoder senhaEncoder() {
        return new BCryptPasswordEncoder();
    }
}

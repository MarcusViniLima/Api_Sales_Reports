package com.example.venda.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



@EnableMethodSecurity
@EnableWebMvc
@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csfr -> csfr.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/user").permitAll()
                        //Clients's rules
                        .requestMatchers(HttpMethod.POST, "/clients").hasRole("SELLER")
                        .requestMatchers("/client/**").hasAnyRole("SUPERVISOR","SELLER")
                        //Sallers's rules
                        .requestMatchers("/seller/**").hasRole("SUPERVISOR")   
                        //Sales's rules
                        .requestMatchers(HttpMethod.DELETE, "/sales/{id}").hasRole("SUPERVISOR")
                        .requestMatchers("/sale/**").hasAnyRole("SUPERVISOR", "SELLER")
                        //Products's rules
                        .requestMatchers(HttpMethod.GET, "/products").hasAnyRole("CLIENT", "SUPERVISOR", "SELLER")
                        .requestMatchers("/product/**").hasAnyRole("SUPERVISOR", "SELLER")
                        .anyRequest().authenticated())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}

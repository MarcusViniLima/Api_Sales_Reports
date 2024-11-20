package com.example.venda.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.venda.jwt.SecurityFilter;
import com.example.venda.repository.UsersRepository;

@EnableMethodSecurity
@EnableWebMvc
@Configuration
public class WebSecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csfr -> csfr.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        // Auth's rules
                        // .requestMatchers(HttpMethod.POST, "/auth").hasRole("SUPERVISOR")
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        // Clients's rules
                        .requestMatchers(HttpMethod.POST, "/client").hasRole("SELLER")
                        .requestMatchers("/client/**").hasAnyRole("SUPERVISOR", "SELLER")
                        // Sallers's rules
                        .requestMatchers("/seller/**").hasRole("SUPERVISOR")
                        // Sales's rules
                        .requestMatchers(HttpMethod.DELETE, "/sale/{id}").hasRole("SUPERVISOR")
                        .requestMatchers("/sale/**").hasAnyRole("SUPERVISOR", "SELLER")
                        // Products's rules
                        .requestMatchers(HttpMethod.GET, "/product").hasAnyRole("CLIENT", "SUPERVISOR", "SELLER")
                        .requestMatchers("/product/**").hasAnyRole("SUPERVISOR", "SELLER")
                        .anyRequest().authenticated())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
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

    @Bean
    public UserDetailsService userDetailsService(UsersRepository usersRepository) {
        return email -> usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }

}

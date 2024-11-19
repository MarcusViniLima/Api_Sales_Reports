package com.example.venda.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.venda.config.security.WebSecurityConfig;
import com.example.venda.dto.AuthenticationRegister;
import com.example.venda.dto.AuthenticationResponse;
import com.example.venda.entities.Users;
import com.example.venda.jwt.JwtService;
import com.example.venda.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private WebSecurityConfig webSecurityConfig;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public Users save(AuthenticationRegister users) throws Exception {
        if (usersRepository.existsByEmail(users.getEmail())) {
            throw new Exception(String.format("Email '%s' já cadastrado", users.getEmail()));
        }
        try {
            var user = new Users(users.getEmail(), users.getPassword(), users.getRole());
            user.setPassword(webSecurityConfig.passwordEncoder().encode(users.getPassword()));
            return usersRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Erro to save user", e);
        }
    }

    public String login(AuthenticationResponse user) throws Exception {
        if (!usersRepository.existsByEmail(user.getEmail())) {
            throw new Exception(String.format("Email '%s' não cadastrado", user.getEmail()));
        }
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            var auth = authenticationManager.authenticate(usernamePassword);

            if (auth.getPrincipal() instanceof UserDetails) {
                var principal = (UserDetails) auth.getPrincipal();
                return jwtService.generateToken(principal);
            } else {
                throw new Exception("Erro ao autenticar usuário");
            }

        } catch (Exception e) {
            throw new Exception("Erro ao fazer login do usuário", e);
        }
    }

    public Users findByEmail(String email) {
        try {
            return usersRepository.findByEmail(email).get();
        } catch (Exception e) {
            throw new RuntimeException("User doesn't exist", e);
        }
    }
}

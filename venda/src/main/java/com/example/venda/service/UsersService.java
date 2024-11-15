package com.example.venda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.venda.entities.Users;
import com.example.venda.repository.UsersRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Users save(Users users) throws Exception {
        try {
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            return usersRepository.save(users);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new Exception(String.format("Username '%s' já cadastrado", users.getUsername()));
        }
    }

    @Transactional
    public Users FindById(Long id) {
        return usersRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
    }

    public List<Users> FindAll() {
        return usersRepository.findAll();
    }



}

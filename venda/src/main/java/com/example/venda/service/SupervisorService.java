package com.example.venda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.venda.config.security.WebSecurityConfig;
import com.example.venda.dto.AuthenticationRegister;
import com.example.venda.entities.Supervisor;
import com.example.venda.entities.Enum.AcessLevels;
import com.example.venda.repository.SupervisorRepository;

import jakarta.transaction.Transactional;

@Service
public class SupervisorService {

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private UsersService usersService;
    @Autowired
    private WebSecurityConfig webSecurityConfig;
    
    @Transactional
    public Supervisor save(Supervisor supervisor){
        if(supervisor == null){
            throw new IllegalArgumentException("Supervisor cannot be empty");
        }
        if(supervisorRepository.existsByEmail(supervisor.getEmail())){
            throw new IllegalArgumentException("Supervisor with email " + supervisor.getEmail() + " already exists");
        }

        try {
            String encodedPassword = webSecurityConfig.passwordEncoder().encode(supervisor.getPassword());
            supervisor.setPassword(encodedPassword);

            AuthenticationRegister user = new AuthenticationRegister(supervisor.getEmail(), encodedPassword,
                    AcessLevels.ROLE_SUPERVISOR);
            usersService.save(user);
            System.out.println("Supervisor saved in users table");
            return supervisorRepository.save(supervisor);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Integrity violation while saving supervisor: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException("Erro to save supervisor", e);
        }
    } 

    @Transactional
    public void delete(String email){
        Supervisor supervisorbd  = supervisorRepository.findByEmail(email).get();
        try {
            supervisorRepository.delete(supervisorbd);
        } catch (Exception e) {
            throw new RuntimeException("Erro to delete supervisor", e);
        }
        
    }

    @Transactional
    public Supervisor update(Supervisor supervisor, String email){
        Supervisor supervisorbd = supervisorRepository.findByEmail(email).get();
        supervisorbd.setName(supervisor.getName());
        supervisorbd.setEmail(supervisor.getEmail());
        
        try {
            return supervisorRepository.save(supervisorbd);
        } catch (Exception e) {
            throw new RuntimeException("Erro to update supervisor", e);
        }
    }

    public Optional<Supervisor> findByEmail(String email){
        try {
            return supervisorRepository.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Supervisor doesn't exist", e);
        }
    }

    public List<Supervisor> findAll(){
        List<Supervisor> supervisores = supervisorRepository.findAll();
        return supervisores;
    }
}

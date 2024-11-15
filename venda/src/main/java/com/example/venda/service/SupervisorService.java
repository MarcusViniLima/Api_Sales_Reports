package com.example.venda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import com.example.venda.entities.Supervisor;
import com.example.venda.repository.SupervisorRepository;

import jakarta.transaction.Transactional;

@Service
public class SupervisorService {

    @Autowired
    private SupervisorRepository supervisorRepository;
    
    @Transactional
    public Supervisor save(Supervisor supervisor){
        if(supervisor == null){
            throw new IllegalArgumentException("Supervisor não pode ser vazio");
        }
        if(supervisorRepository.existsById(supervisor.getId())){
            throw new IllegalArgumentException("Supervisor já existe");
        }

        try {
            return supervisorRepository.save(supervisor);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar supervisor", e);
        }
    } 

    @Transactional
    public void delete(Long id){
        if(!supervisorRepository.existsById(id)){
            throw new IllegalArgumentException("Supervisor não existe");
        }
        supervisorRepository.deleteById(id);
    }

    @Transactional
    public Supervisor update(Supervisor supervisor, Long id){
        Supervisor supervisorbd = this.findById(id);
        supervisorbd.setId(id);
        supervisorbd.setName(supervisor.getName());
        supervisorbd.setEmail(supervisor.getEmail());
        
        try {
            return supervisorRepository.save(supervisorbd);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar supervisor", e);
        }
    }

    public Supervisor findById(Long id){
        Supervisor supervisor = supervisorRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supervisor não encontrado"));
            return supervisor;
    }

    public List<Supervisor> findAll(){
        List<Supervisor> supervisores = supervisorRepository.findAll();
        return supervisores;
    }
}

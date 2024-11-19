package com.example.venda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.venda.entities.Client;
import com.example.venda.repository.ClientRepository;

import jakarta.transaction.Transactional;

@Service
public class ClientService {
    
    @Autowired
    ClientRepository clienteRepository;

    @Transactional
    public Client save(Client cliente){
        if(cliente == null){
            throw new IllegalArgumentException("Cliente cannot be empty");
        }
        if(clienteRepository.existsByEmail(cliente.getEmail())){
            throw new IllegalArgumentException("Client already exists");
        }

        try {
            return clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Erro to save client", e);
        }
    }

    @Transactional
    public void delete(String email){
        Client clientebd = clienteRepository.findByEmail(email).get();
        try {
            clienteRepository.delete(clientebd);
        } catch (Exception e) {
            throw new RuntimeException("Erro to delete client", e);
        }

    }

    public List<Client> findAll(){
         List<Client> clientes = clienteRepository.findAll();
         return clientes;
    }

    public Optional<Client> findByEmail(String email){
        try {
            return clienteRepository.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Client doesn't exist", e);
        }
    }

    public Client update(Client cliente, String email){
        Client clientbd = this.findByEmail(email).get();
        clientbd.setName(cliente.getName());
        clientbd.setEmail(cliente.getEmail());
        
        try {
            return clienteRepository.save(clientbd);
        } catch (Exception e) {
            throw new RuntimeException("Erro to update client", e);
        }
    }

}

package com.example.venda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            throw new IllegalArgumentException("cliente não pode ser vazio");
        }
        if(clienteRepository.existsById(cliente.getId())){
            throw new IllegalArgumentException("Cliente já existe");
        }

        try {
            return clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente", e);
        }
    }

    @Transactional
    public void delete(Long id){
        Client cliente = this.findById(id);
        clienteRepository.delete(cliente);

    }

    public List<Client> findAll(){
         List<Client> clientes = clienteRepository.findAll();
         return clientes;
    }

    public Client findById(Long id){
        Client cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
            return cliente;
    }

    public Client update(Client cliente, Long id){
        Client clientbd = this.findById(id);
        clientbd.setId(id);
        clientbd.setName(cliente.getName());
        clientbd.setEmail(cliente.getEmail());
        
        try {
            return clienteRepository.save(clientbd);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente", e);
        }


    }

}

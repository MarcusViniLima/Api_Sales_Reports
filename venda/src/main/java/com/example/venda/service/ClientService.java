package com.example.venda.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.venda.config.security.WebSecurityConfig;
import com.example.venda.dto.AuthenticationRegister;
import com.example.venda.entities.Client;
import com.example.venda.entities.Enum.AcessLevels;
import com.example.venda.repository.ClientRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class ClientService {
    
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    UsersService usersService;
    @Autowired
    private WebSecurityConfig webSecurityConfig;
    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public Client save(Client client){
        if(client == null){
            throw new IllegalArgumentException("Cliente cannot be empty");
        }
        if(clientRepository.existsByEmail(client.getEmail())){
            throw new IllegalArgumentException("Client  with email " + client.getEmail() + " already exists");
        }

        try {
            String encodedPassword = webSecurityConfig.passwordEncoder().encode(client.getPassword());
            client.setPassword(encodedPassword);

            AuthenticationRegister user = new AuthenticationRegister(client.getEmail(), encodedPassword,
                    AcessLevels.ROLE_CLIENT);
            usersService.save(user);
            System.out.println("Client saved in users table");
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Integrity violation while saving client: " + e.getMessage(), e);
        }catch (Exception e) {
            throw new RuntimeException("Erro to save client", e);
        }
    }

    @Transactional
    public void delete(String email){
        Client clientbd = clientRepository.findByEmail(email).get();
        try {
            clientRepository.delete(clientbd);
        } catch (Exception e) {
            throw new RuntimeException("Erro to delete client", e);
        }

    }

    public List<Client> findAll(){
         List<Client> clients = clientRepository.findAll();
         return clients;
    }

    public Optional<Client> findByEmail(String email){
        try {
            return clientRepository.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Client doesn't exist", e);
        }
    }

    public Client update(Client client, String email){
        Client clientbd = this.findByEmail(email).get();
        clientbd.setName(client.getName());
        clientbd.setEmail(client.getEmail());
        
        try {
            return clientRepository.save(clientbd);
        } catch (Exception e) {
            throw new RuntimeException("Erro to update client", e);
        }
    }

    public void registerClientsFromJson() throws Exception {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Starting the registration of clients from the JSON file");
    
        ClassPathResource resource = new ClassPathResource("clients.json");
    
        try (InputStream inputStream = resource.getInputStream()) {
            List<Client> clients = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Client>>() {
                    });
            for (Client client : clients) {
                this.save(client);
            }
            logger.info("Client registration completed successfully: {} clients registered.", clients.size());
        } catch (IOException e) {
            logger.error("Error loading the JSON file", e);
            throw new RuntimeException("Error loading the JSON file", e);
        }
    }
    

}

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
import com.example.venda.entities.Seller;
import com.example.venda.entities.Enum.AcessLevels;
import com.example.venda.repository.SellerRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    UsersService usersService;
    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Transactional
    public Seller save(Seller seller) {
        if (seller == null) {
            throw new IllegalArgumentException("Seller cannot be empty");
        }

        if (sellerRepository.existsByEmail(seller.getEmail())) {
            throw new IllegalArgumentException("Seller with email " + seller.getEmail() + " already exists");
        }

        try {
            String encodedPassword = webSecurityConfig.passwordEncoder().encode(seller.getPassword());
            seller.setPassword(encodedPassword);

            AuthenticationRegister user = new AuthenticationRegister(seller.getEmail(), encodedPassword,
                    AcessLevels.ROLE_SELLER);
            usersService.save(user);
            System.out.println("Seller saved in users table");

            return sellerRepository.save(seller);

        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Integrity violation while saving seller: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving seller", e);
        }
    }

    @Transactional
    public void delete(String email) {
        Seller sellerbd = sellerRepository.findByEmail(email).get();
        try {
            sellerRepository.delete(sellerbd);
        } catch (Exception e) {
            throw new RuntimeException("Erro to delete seller", e);
        }

    }

    public List<Seller> findAll() {
        List<Seller> vendedores = sellerRepository.findAll();
        return vendedores;
    }

    public Optional<Seller> findByEmail(String email) {
        try {
            return sellerRepository.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Seller doesn't exist", e);
        }
    }

    public Seller update(Seller vendedor, String email) {
        Seller vendedorbd = this.findByEmail(email).get();
        vendedorbd.setName(vendedor.getName());
        vendedorbd.setEmail(vendedor.getEmail());

        try {
            return sellerRepository.save(vendedorbd);
        } catch (Exception e) {
            throw new RuntimeException("Erro to update seller", e);
        }

    }

    public void registerSellersFromJson() throws Exception {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Starting the registration of sellers from the JSON file");
    
        ClassPathResource resource = new ClassPathResource("sellers.json");
    
        try (InputStream inputStream = resource.getInputStream()) {
            List<Seller> sellers = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Seller>>() {
                    });
            sellerRepository.saveAll(sellers);
            logger.info("Seller registration completed successfully: {} sellers registered.", sellers.size());
        } catch (IOException e) {
            logger.error("Error loading the JSON file", e);
            throw new RuntimeException("Error loading the JSON file", e);
        }
    }

}

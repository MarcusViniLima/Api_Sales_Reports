package com.example.venda.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.venda.entities.Product;
import com.example.venda.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public Product save(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be empty");
        }
        if (productRepository.existsByCode(product.getCode())) {
            throw new IllegalArgumentException("Product already exists");
        }

        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Erro to save product", e);
        }
    }

    @Transactional
    public void delete(String code) {
        Product produto = this.findByCode(code).get();
        try {
            productRepository.delete(produto);
        } catch (Exception e) {
            throw new RuntimeException("Erro to delete product", e);
        }

    }

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    public Optional<Product> findByCode(String code) {
        try {
            return productRepository.findByCode(code);
        } catch (Exception e) {
            throw new RuntimeException("Product doesn't exist", e);
        }
    }

    public Product update(Product product, String code) {
        Product productbd = this.findByCode(code).get();
        productbd.setName(product.getName());
        productbd.setBrand(product.getBrand());
        productbd.setQuantity(product.getQuantity());

        try {
            return productRepository.save(productbd);
        } catch (Exception e) {
            throw new RuntimeException("Erro to update product", e);
        }

    }

    public void registerProductsFromJson() throws Exception {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Starting the registration of products from the JSON file");
    
        ClassPathResource resource = new ClassPathResource("products.json");
    
        try (InputStream inputStream = resource.getInputStream()) {
            List<Product> products = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Product>>() {
                    });
            productRepository.saveAll(products);
            logger.info("Product registration completed successfully: {} products registered.", products.size());
        } catch (IOException e) {
            logger.error("Error loading the JSON file", e);
            throw new RuntimeException("Error loading the JSON file", e);
        }
    }
    
}

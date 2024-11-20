package com.example.venda.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venda.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{

    boolean existsByCode(String code);
    Optional<Product> findByCode(String code);


}

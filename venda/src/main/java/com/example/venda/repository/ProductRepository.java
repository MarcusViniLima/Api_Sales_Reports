package com.example.venda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venda.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    boolean existsById(Long id);

}

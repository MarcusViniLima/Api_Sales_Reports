package com.example.venda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venda.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository <Sale, Long> {

    boolean existsById(Long id);

}

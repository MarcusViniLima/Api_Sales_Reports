package com.example.venda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venda.entities.Seller;


@Repository
public interface SellerRepository extends JpaRepository<Seller, Long>{

    boolean existsById(Long id);
}

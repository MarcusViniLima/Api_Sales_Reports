package com.example.venda.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venda.entities.Seller;


@Repository
public interface SellerRepository extends JpaRepository<Seller, UUID>{

    boolean existsByEmail(String email);
    Optional<Seller> findByEmail(String email);
}

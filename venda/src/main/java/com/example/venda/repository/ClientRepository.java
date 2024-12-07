package com.example.venda.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venda.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID>{

    boolean existsByEmail(String email);

    Optional<Client> findByEmail(String email);

}

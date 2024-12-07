package com.example.venda.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venda.entities.Supervisor;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, UUID> {
    boolean existsByEmail(String email);

    Optional<Supervisor> findByEmail(String email);
}

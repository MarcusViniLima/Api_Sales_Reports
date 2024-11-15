package com.example.venda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venda.entities.Supervisor;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
    boolean existsById(Long id);
}

package com.example.venda.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.venda.entities.Users;
@Repository
public interface UsersRepository extends JpaRepository<Users,Long>{
    boolean existsByEmail(String email);
    Optional<Users> findByEmail(String email);

}

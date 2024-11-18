package com.example.venda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venda.entities.Supervisor;
import com.example.venda.service.SupervisorService;

@RestController
@RequestMapping("/supervisor")
public class SupervisorController {
    @Autowired
    private SupervisorService supervisorService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Supervisor supervisor) {
        supervisorService.save(supervisor);
        return ResponseEntity.status(HttpStatus.OK).body(supervisor);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<Supervisor> supervisores = supervisorService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(supervisores);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> findById(@PathVariable String email) {
        Supervisor supervisor = supervisorService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(supervisor);
    }

    @PutMapping("/{email}")
    public ResponseEntity<Object> update(@RequestBody Supervisor supervisor, @PathVariable String email) {
        supervisorService.update(supervisor, email);
        return ResponseEntity.status(HttpStatus.OK).body(supervisor);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Object> delete(@PathVariable String email) {
        supervisorService.delete(email);
        return ResponseEntity.status(HttpStatus.OK).body("Sucess to delete supervisor.");
    }

}

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
@RequestMapping("/supervisors")
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

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Supervisor supervisor = supervisorService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(supervisor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Supervisor supervisor, @PathVariable Long id) {
        supervisorService.update(supervisor, id);
        return ResponseEntity.status(HttpStatus.OK).body(supervisor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        supervisorService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Supervisor deletado com sucesso.");
    }

}

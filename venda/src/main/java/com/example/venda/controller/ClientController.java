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

import com.example.venda.entities.Client;
import com.example.venda.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clienteService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Client cliente) {
        clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @GetMapping
    public List<Client> getAll() {
        return clienteService.findAll();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Object> delete(@PathVariable String email) {
        clienteService.delete(email);
        return ResponseEntity.status(HttpStatus.OK).body("Sucess to delete client.");
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> findByid(@PathVariable String email) {
        Client cliente = clienteService.findByEmail(email).get();
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @PutMapping("/{email}")
    public ResponseEntity<Object> update(@RequestBody Client cliente, @PathVariable String email) {
        clienteService.update(cliente, email);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

}

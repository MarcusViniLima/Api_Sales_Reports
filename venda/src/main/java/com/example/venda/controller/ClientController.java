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
@RequestMapping("/clientes")
public class ClientController {

    @Autowired
    ClientService clienteService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Client cliente){
        clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @GetMapping
    public List<Client> getAll(){
        return clienteService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        clienteService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado com sucesso.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findByid(@PathVariable Long id){
        Client cliente = clienteService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Client cliente, @PathVariable Long id){
        clienteService.update(cliente, id);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

}

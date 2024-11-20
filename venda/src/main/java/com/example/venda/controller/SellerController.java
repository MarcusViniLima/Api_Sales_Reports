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

import com.example.venda.entities.Seller;
import com.example.venda.service.SellerService;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService vendedorService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Seller vendedor){
        vendedorService.save(vendedor);
        return ResponseEntity.status(HttpStatus.OK).body(vendedor);
    }

    @GetMapping
    public List<Seller> getAll(){
        return vendedorService.findAll();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Object> delete(@PathVariable String email){
        vendedorService.delete(email);
        return ResponseEntity.status(HttpStatus.OK).body("Sucess to delete seller.");
    }

    @GetMapping("/{email}")
    public ResponseEntity<Object> findByid(@PathVariable String email){
        Seller vendedor = vendedorService.findByEmail(email).get();
        return ResponseEntity.status(HttpStatus.OK).body(vendedor);
    }

    @PutMapping("/{email}")
    public ResponseEntity<Object> update(@RequestBody Seller vendedor, @PathVariable String email){
        vendedorService.update(vendedor, email);
        return ResponseEntity.status(HttpStatus.OK).body(vendedor);
    }

}

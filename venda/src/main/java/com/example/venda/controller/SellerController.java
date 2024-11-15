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
@RequestMapping("/vendedor")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        vendedorService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Vendedor deletado com sucesso.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findByid(@PathVariable Long id){
        Seller vendedor = vendedorService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(vendedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Seller vendedor, @PathVariable Long id){
        vendedorService.update(vendedor, id);
        return ResponseEntity.status(HttpStatus.OK).body(vendedor);
    }

}

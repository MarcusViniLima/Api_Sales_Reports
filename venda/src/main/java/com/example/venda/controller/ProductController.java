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


import com.example.venda.entities.Product;
import com.example.venda.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService produtoService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Product produto) {
        produtoService.save(produto);
        return ResponseEntity.status(HttpStatus.OK).body(produto);
    }

    @GetMapping
    public List<Product> getAll() {
        return produtoService.findAll();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Object> delete(@PathVariable String code) {
        produtoService.delete(code);
        return ResponseEntity.status(HttpStatus.OK).body("Sucess to delete product.");
    }

    @GetMapping("/{code}")
    public ResponseEntity<Object> findByid(@PathVariable String code) {
        Product produto = produtoService.findByCode(code).get();
        return ResponseEntity.status(HttpStatus.OK).body(produto);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Object> update(@RequestBody Product produto, @PathVariable String code) {
        produtoService.update(produto, code);
        return ResponseEntity.status(HttpStatus.OK).body(produto);
    }

    @PostMapping("/register")
    public String registerProducts() {
        try {
            produtoService.registerProductsFromJson();
            return "Products registered successfully!";
        } catch (Exception e) {
            return "Error registering products: " + e.getMessage();
        }
    }

}

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
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    ProductService produtoService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Product produto){
        produtoService.save(produto);
        return ResponseEntity.status(HttpStatus.OK).body(produto);
    }

    @GetMapping
    public List<Product> getAll(){
        return produtoService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        produtoService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findByid(@PathVariable Long id){
        Product produto = produtoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Product produto, @PathVariable Long id){
        produtoService.update(produto, id);
        return ResponseEntity.status(HttpStatus.OK).body(produto);
    }

}

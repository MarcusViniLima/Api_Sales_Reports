package com.example.venda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.venda.entities.Product;
import com.example.venda.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    ProductRepository produtoRepository;

    @Transactional
    public Product save(Product produto){
        if(produto == null){
            throw new IllegalArgumentException("Product cannot be empty");
        }
        if(produtoRepository.existsByCode(produto.getCode())){
            throw new IllegalArgumentException("Product already exists");
        }

        try {
            return produtoRepository.save(produto);
        } catch (Exception e) {
            throw new RuntimeException("Erro to save product", e);
        }
    }

    @Transactional
    public void delete(String code){
        Product produto = this.findByCode(code).get();
        try { 
            produtoRepository.delete(produto);
        } catch (Exception e) {
            throw new RuntimeException("Erro to delete product", e);
        }

    }

    public List<Product> findAll(){
         List<Product> produtos = produtoRepository.findAll();
         return produtos;
    }

    public Optional<Product> findByCode(String code){
        try {
            return produtoRepository.findByCode(code);
        } catch (Exception e) {
            throw new RuntimeException("Product doesn't exist", e);
        }
    }

    public Product update(Product produto, String code){
        Product produtobd = this.findByCode(code).get();
        produtobd.setName(produto.getName());
        produtobd.setBrand(produto.getBrand());
        produtobd.setQuantity(produto.getQuantity());
        
        try {
            return produtoRepository.save(produtobd);
        } catch (Exception e) {
            throw new RuntimeException("Erro to update product", e);
        }


    }

    public long countProducts() {
        return produtoRepository.count();
    }
    

}

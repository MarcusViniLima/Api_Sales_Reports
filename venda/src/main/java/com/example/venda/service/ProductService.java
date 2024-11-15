package com.example.venda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            throw new IllegalArgumentException("Produto cnão pode ser vazio");
        }
        if(produtoRepository.existsById(produto.getId())){
            throw new IllegalArgumentException("Produto já existe");
        }

        try {
            return produtoRepository.save(produto);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar produto", e);
        }
    }

    @Transactional
    public void delete(Long id){
        Product produto = this.findById(id);
        produtoRepository.delete(produto);

    }

    public List<Product> findAll(){
         List<Product> produtos = produtoRepository.findAll();
         return produtos;
    }

    public Product findById(Long id){
        Product produto = produtoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
            return produto;
    }

    public Product update(Product produto, Long id){
        Product produtobd = this.findById(id);
        produtobd.setId(id);
        produtobd.setName(produto.getName());
        produtobd.setBrand(produto.getBrand());
        produtobd.setQuantity(produto.getQuantity());
        
        try {
            return produtoRepository.save(produtobd);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar produto", e);
        }


    }

}

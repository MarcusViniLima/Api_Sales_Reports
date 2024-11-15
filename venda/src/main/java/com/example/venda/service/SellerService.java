package com.example.venda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.venda.entities.Seller;
import com.example.venda.repository.SellerRepository;

import jakarta.transaction.Transactional;

@Service
public class SellerService {

    @Autowired
    SellerRepository vendedorRepository;

    @Transactional
    public Seller save(Seller vendedor){
        if(vendedor == null){
            throw new IllegalArgumentException("Vendedor cnão pode ser vazio");
        }
        if(vendedorRepository.existsById(vendedor.getId())){
            throw new IllegalArgumentException("Vendedor já existe");
        }

        try {
            return vendedorRepository.save(vendedor);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar vendedor", e);
        }
    }

    @Transactional
    public void delete(Long id){
        Seller vendedor = this.findById(id);
        try {
            vendedorRepository.delete(vendedor);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar vendedor", e);
        }
        

    }

    public List<Seller> findAll(){
         List<Seller> vendedores = vendedorRepository.findAll();
         return vendedores;
    }

    public Seller findById(Long id){
        Seller vendedor = vendedorRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendedor não encontrado"));
            return vendedor;
    }

    public Seller update(Seller vendedor, Long id){
        Seller vendedorbd = this.findById(id);
        vendedorbd.setId(id);
        vendedorbd.setName(vendedor.getName());
        vendedorbd.setEmail(vendedor.getEmail());
        
        try {
            return vendedorRepository.save(vendedorbd);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar vendedor", e);
        }


    }

}

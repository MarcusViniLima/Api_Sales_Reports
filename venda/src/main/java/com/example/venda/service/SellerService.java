package com.example.venda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.venda.entities.Seller;
import com.example.venda.repository.SellerRepository;

import jakarta.transaction.Transactional;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Transactional
    public Seller save(Seller seller){
        if(seller == null){
            throw new IllegalArgumentException("Seller cannot be empty");
        }
        if(sellerRepository.existsByEmail(seller.getEmail())){
            throw new IllegalArgumentException("Seller already exists");
        }

        try {
            return sellerRepository.save(seller);
        } catch (Exception e) {
            throw new RuntimeException("Erro to save seller", e);
        }
    }

    @Transactional
    public void delete(String email){
        Seller sellerbd = sellerRepository.findByEmail(email).get();
        try {
            sellerRepository.delete(sellerbd);
        } catch (Exception e) {
            throw new RuntimeException("Erro to delete seller", e);
        }
        

    }

    public List<Seller> findAll(){
         List<Seller> vendedores = sellerRepository.findAll();
         return vendedores;
    }

    public Optional<Seller> findByEmail(String email){
        try {
            return sellerRepository.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Seller doesn't exist", e);
        }
    }

    public Seller update(Seller vendedor, String email){
        Seller vendedorbd = this.findByEmail(email).get();
        vendedorbd.setName(vendedor.getName());
        vendedorbd.setEmail(vendedor.getEmail());
        
        try {
            return sellerRepository.save(vendedorbd);
        } catch (Exception e) {
            throw new RuntimeException("Erro to update seller", e);
        }


    }

}

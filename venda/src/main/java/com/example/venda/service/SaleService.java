package com.example.venda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.venda.entities.Product;
import com.example.venda.entities.Sale;
import com.example.venda.repository.SaleRepository;

import jakarta.transaction.Transactional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;

    @Transactional
    public Sale save(Sale sale){
        if(sale == null){
            throw new IllegalArgumentException("Venda nula");
        }
        if(sale.getIdClient() == null){
            throw new IllegalArgumentException("Cliente nulo");
        }
        if(sale.getIdSeller() == null){
            throw new IllegalArgumentException("Vendedor nulo");
        }
        if(sale.getIdProduct() == null){
            throw new IllegalArgumentException("Produto nulo");
        }
        if(clientService.findById(sale.getIdClient()) == null){
            throw new IllegalArgumentException("Cliente nao cadastrado");
        }
        if(sellerService.findById(sale.getIdSeller()) == null){
            throw new IllegalArgumentException("Vendedor nao cadastrado");
        }
        if(productService.findById(sale.getIdProduct()) == null){
            throw new IllegalArgumentException("Produto nao cadastrado");
        }
        Product product = productService.findById(sale.getIdProduct());
        product.setQuantity(product.getQuantity() - sale.getQuantityProdutc());
        productService.update(product, sale.getIdProduct());
        try {
            return saleRepository.save(sale);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar venda", e);
        }
        
    }

    @Transactional
    public void delete(Long id) {
        Sale sale = this.findById(id);
        Product product = productService.findById(sale.getIdProduct());
        product.setQuantity(product.getQuantity() + sale.getQuantityProdutc());
        productService.update(product, sale.getIdProduct());
        try {
            saleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar venda", e);
        }
    }

    public Sale findById(Long id){
        Sale sale = saleRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda nao encontrada"));
        return sale;
        
    }

    public List<Sale> findAll(){
        List<Sale> sales = saleRepository.findAll();
        return sales;
    }
}

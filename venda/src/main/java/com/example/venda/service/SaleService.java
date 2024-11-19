package com.example.venda.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.venda.dto.SaleCreateDTO;
import com.example.venda.dto.mapper.SaleMapper;
import com.example.venda.entities.Client;
import com.example.venda.entities.Product;
import com.example.venda.entities.Sale;
import com.example.venda.entities.Seller;
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
    public Sale save(SaleCreateDTO dto) {

        if(dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        Client client = clientService.findByEmail(dto.getClientEmail())
                .orElseThrow(() -> new IllegalArgumentException("Client doesn't exist"));
        
        Seller seller = sellerService.findByEmail(dto.getSellerEmail())
                .orElseThrow(() -> new IllegalArgumentException("Seller doesn't exist")); 

        Product product = productService.findByCode(dto.getProductCode())
                .orElseThrow(() -> new IllegalArgumentException("Product doesn't exist"));

        Sale sale = SaleMapper.toEntity(dto, client, seller, product);

        if (product.getQuantity() < dto.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product quantity is not enough");
        }
        sale.setPriceSale(product.getPrice().multiply(BigDecimal.valueOf(sale.getQuantityProdutc())));
        product.setQuantity(product.getQuantity() - sale.getQuantityProdutc());
        productService.update(product, product.getCode());

        try {
            return saleRepository.save(sale);
        } catch (Exception e) {
            throw new RuntimeException("Erro to save sale", e);
        }

    }

    @Transactional
    public void delete(Long id) {
        Sale sale = this.findById(id);
        Product product = productService.findByCode(sale.getProduct().getCode()).get();
        product.setQuantity(product.getQuantity() + sale.getQuantityProdutc());
        productService.update(product, sale.getProduct().getCode());
        try {
            saleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro to delete sale", e);
        }
    }

    public Sale findById(Long id){
       try {
        Sale sale = saleRepository.findById(id).get();
        return sale;
       } catch (Exception e) {
        throw new RuntimeException("Sale doesn't exist", e);
       }
         
        
    }

    public List<Sale> findAll(){
        List<Sale> sales = saleRepository.findAll();
        return sales;
    }
}

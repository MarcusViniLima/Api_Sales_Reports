package com.example.venda.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public Sale save(SaleCreateDTO dto) {

        if(dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if(saleRepository.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("Sale already exists");
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
        sale.setPriceSale(product.getPrice().multiply(BigDecimal.valueOf(sale.getQuantityProduct())));
        product.setQuantity(product.getQuantity() - sale.getQuantityProduct());
        productService.update(product, product.getCode());
        client.setSales(sale);
        seller.setSales(sale);
        product.setSales(sale);

        try {
            return saleRepository.save(sale);
        } catch (Exception e) {
            throw new RuntimeException("Erro to save sale", e);
        }

    }

    @Transactional
    public void delete(Long code) {
        Sale sale = this.findByCode(code);
        Product product = productService.findByCode(sale.getProduct().getCode()).get();
        product.setQuantity(product.getQuantity() + sale.getQuantityProduct());
        productService.update(product, sale.getProduct().getCode());
        try {
            saleRepository.delete(sale);
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

    public Sale findByCode(Long code){
        try {
            Sale sale = saleRepository.findByCode(code).get();
            return sale;
        } catch (Exception e) {
            throw new RuntimeException("Sale doesn't exist", e);
        }
    }

    public void registerSalesFromJson() throws Exception {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("Starting the registration of Sales from the JSON file");
    
        ClassPathResource resource = new ClassPathResource("sales.json");
    
        try (InputStream inputStream = resource.getInputStream()) {
            List<SaleCreateDTO> salesDTO = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<SaleCreateDTO>>() {
                    });
    
            for (SaleCreateDTO dto : salesDTO) {
                this.save(dto);
            }
    
            logger.info("Sale registration completed successfully: {} Sales registered.", salesDTO.size());
        } catch (IOException e) {
            logger.error("Error loading the JSON file", e);
            throw new RuntimeException("Error loading the JSON file", e);
        }
    }
    
    
}

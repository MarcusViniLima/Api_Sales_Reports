package com.example.venda.config.initialise;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.venda.service.ClientService;
import com.example.venda.service.ProductService;

import com.example.venda.service.SellerService;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductService productService;
    private final ClientService clientService;
    private final SellerService sellerService;


    
    public DataLoader(ProductService productService, 
                      ClientService clientService, 
                      SellerService sellerService) {
        this.productService = productService;
        this.clientService = clientService;
        this.sellerService = sellerService;

    }

    @Override
    public void run(String... args) throws Exception {
        productService.registerProductsFromJson();     
        sellerService.registerSellersFromJson(); 
        clientService.registerClientsFromJson();  
    }
}
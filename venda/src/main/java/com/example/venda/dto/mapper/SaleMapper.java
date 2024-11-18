package com.example.venda.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.venda.dto.SaleCreateDTO;
import com.example.venda.entities.Client;
import com.example.venda.entities.Product;
import com.example.venda.entities.Sale;
import com.example.venda.entities.Seller;

@Component
public class SaleMapper {

   public static Sale toEntity(SaleCreateDTO dto, Client client, Seller seller, Product product) {
        Sale sale = new Sale();
        sale.setClient(client); 
        sale.setSeller(seller); 
        sale.setProduct(product); 
        sale.setQuantityProdutc(dto.getQuantity());
        sale.setPriceSale(null);
        return sale;
    }
}

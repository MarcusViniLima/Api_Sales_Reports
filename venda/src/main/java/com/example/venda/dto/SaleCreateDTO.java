package com.example.venda.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SaleCreateDTO {

    private String clientEmail;
    private String sellerEmail;
    private String productCode;
    private int quantity;
    
}

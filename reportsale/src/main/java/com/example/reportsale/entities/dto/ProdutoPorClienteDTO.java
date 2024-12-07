package com.example.reportsale.entities.dto;

import lombok.Data;

@Data
public class ProdutoPorClienteDTO {

    private String clientName;
    private String productName;
    private int totalQuantityPurchased;

    public ProdutoPorClienteDTO(String clientName, String productName, int totalQuantityPurchased) {
        this.clientName = clientName;
        this.productName = productName;
        this.totalQuantityPurchased = totalQuantityPurchased;
    }

}

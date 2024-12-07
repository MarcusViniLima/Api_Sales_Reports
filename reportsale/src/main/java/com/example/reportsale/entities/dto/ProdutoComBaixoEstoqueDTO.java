package com.example.reportsale.entities.dto;

import lombok.Data;

@Data
public class ProdutoComBaixoEstoqueDTO {
    private String productName;
    private int currentStock;

    public ProdutoComBaixoEstoqueDTO(String productName, int currentStock) {
        this.productName = productName;
        this.currentStock = currentStock;
    }
}

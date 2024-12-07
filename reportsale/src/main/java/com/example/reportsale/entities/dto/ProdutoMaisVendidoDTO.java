package com.example.reportsale.entities.dto;



import lombok.Data;

@Data
public class ProdutoMaisVendidoDTO {
    private String productName;
    private int totalQuantitySold;

    public ProdutoMaisVendidoDTO(String productName, int totalQuantitySold) {
        this.productName = productName;
        this.totalQuantitySold = totalQuantitySold;
    }
}

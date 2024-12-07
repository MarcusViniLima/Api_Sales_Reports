package com.example.reportsale.entities.dto;

import lombok.Data;

@Data
public class ConsumoMedioDTO {
    
    private String clientName;
    private double avgQuantityPurchased;

    public ConsumoMedioDTO(String clientName, double avgQuantityPurchased) {
        this.clientName = clientName;
        this.avgQuantityPurchased = avgQuantityPurchased;
    }
}

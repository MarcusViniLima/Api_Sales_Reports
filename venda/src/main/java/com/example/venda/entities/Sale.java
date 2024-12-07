package com.example.venda.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    private Long code;

    @ManyToOne
    @JoinColumn(name = "id_client")
    @JsonManagedReference
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_seller")
    @JsonManagedReference
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "id_product")
    @JsonManagedReference
    private Product product;

    private int quantityProduct;

    private BigDecimal priceSale;



}

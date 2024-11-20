package com.example.venda.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String code;
    private String name;
    private String brand;
    private BigDecimal price;
    private int quantity;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, price, quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product produto = (Product) obj;
        return Objects.equals(id, produto.id)
                && Objects.equals(name, produto.name)
                && Objects.equals(brand, produto.brand)
                && Objects.equals(price, produto.price)
                && Objects.equals(quantity, produto.quantity);
    }


}

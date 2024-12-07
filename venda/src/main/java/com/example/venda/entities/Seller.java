package com.example.venda.entities;


import java.util.List;
import java.util.Objects;


import com.example.venda.entities.Enum.AcessLevels;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends Member {

    @Enumerated(EnumType.STRING)
    private AcessLevels acessLevels = AcessLevels.ROLE_SELLER;

    @JsonBackReference
    private List<Sale> sales;
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), acessLevels);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj) || getClass() != obj.getClass())
            return false;
        Seller vendedor = (Seller) obj;
        return acessLevels == vendedor.acessLevels;
    }

    public void setSales(Sale sale) {
        this.sales.add(sale);
    }
}

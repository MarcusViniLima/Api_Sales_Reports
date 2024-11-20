package com.example.venda.entities;


import java.util.List;
import java.util.Objects;


import com.example.venda.entities.Enum.AcessLevels;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Member {

    @Enumerated(EnumType.STRING)
    private AcessLevels acessLevels = AcessLevels.ROLE_CLIENT;

    @OneToMany(mappedBy = "client")  // 'client' é o nome do campo na classe Sale
    private List<Sale> sales;
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), acessLevels);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        if (!super.equals(obj))
            return false;
        Client cliente = (Client) obj;
        return acessLevels == cliente.acessLevels;
    }

    
}

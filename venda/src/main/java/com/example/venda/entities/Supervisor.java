package com.example.venda.entities;

import java.util.Objects;


import com.example.venda.entities.Enum.AcessLevels;

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
public class Supervisor extends Users{
    
    @Enumerated(EnumType.STRING)
    private AcessLevels acessLevels = AcessLevels.ROLE_SUPERVISOR;

@Override
public int hashCode() {
    return Objects.hash(super.hashCode(), acessLevels);
}

@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Supervisor)) return false;
    if (!super.equals(obj)) return false;
    Supervisor other = (Supervisor) obj;
    return acessLevels == other.acessLevels;
}


}

package com.example.venda.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@MappedSuperclass @SuperBuilder
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private Date birthDate;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Users usuario = (Users) obj;
        return Objects.equals(id, usuario.id) && Objects.equals(name, usuario.name) && Objects.equals(surname, usuario.surname) && Objects.equals(email, usuario.email) && Objects.equals(birthDate, usuario.birthDate);
    }   

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, birthDate);
    }
}

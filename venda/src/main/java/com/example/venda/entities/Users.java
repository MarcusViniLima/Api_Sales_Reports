package com.example.venda.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.venda.entities.Enum.AcessLevels;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@MappedSuperclass @SuperBuilder
public abstract class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @Email
    private String email;
    private Date birthDate;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least 8 characters, including one uppercase letter, one lowercase letter, one number, and one special character.")
    private String password;

    @Enumerated(EnumType.STRING)
    private AcessLevels acessLevels;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.acessLevels == AcessLevels.ROLE_SUPERVISOR){
            return List.of(
                    new SimpleGrantedAuthority("ROLE_"+ AcessLevels.ROLE_SUPERVISOR.getRole()),
                    new SimpleGrantedAuthority("ROLE_"+ AcessLevels.ROLE_SELLER.getRole()),
                    new SimpleGrantedAuthority("ROLE_"+ AcessLevels.ROLE_CLIENT.getRole()));
        }
        else if (this.acessLevels == AcessLevels.ROLE_SELLER){return List.of(
                new SimpleGrantedAuthority("ROLE_"+ AcessLevels.ROLE_SELLER.getRole()),
                new SimpleGrantedAuthority("ROLE_"+ AcessLevels.ROLE_CLIENT.getRole()));
        }
        else { return List.of(
                new SimpleGrantedAuthority("ROLE_"+ AcessLevels.ROLE_CLIENT.getRole()));
        }
    }

    @Override
    public String getUsername() {
        return email;
    }

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

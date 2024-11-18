package com.example.venda.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.venda.entities.Enum.AcessLevels;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "login")
    private String email;

    @Column(name = "password")
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
}

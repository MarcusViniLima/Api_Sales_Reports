package com.example.venda.entities;

import java.util.Collection;
import java.util.List;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "login", unique = true)
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).*$", message = "Password must contain one uppercase, one lowercase, one number, and one special character.")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private AcessLevels acessLevels;

    public Users(String email, String password, AcessLevels acessLevels) {
        this.email = email;
        this.password = password;
        this.acessLevels = acessLevels;
    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.acessLevels.getRole()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}

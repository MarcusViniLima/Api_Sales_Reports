package com.example.venda.dto;

import com.example.venda.entities.Enum.AcessLevels;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRegister {

    private String email;
    private String password;
    private AcessLevels role;
}

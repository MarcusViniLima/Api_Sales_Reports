package com.example.venda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venda.dto.AuthenticationRegister;
import com.example.venda.dto.AuthenticationResponse;
import com.example.venda.entities.Users;
import com.example.venda.service.UsersService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    UsersService usersService;

    @PostMapping
    public ResponseEntity<Object> create( @RequestBody AuthenticationRegister user) throws Exception {
        Users userCreated = usersService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login( @RequestBody AuthenticationResponse user) throws Exception {
        String userLogin= usersService.login(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userLogin);
    }

}

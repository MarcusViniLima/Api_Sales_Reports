package com.example.venda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venda.entities.Users;
import com.example.venda.service.UsersService;




@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<Object> create( @RequestBody Users user) throws Exception {
        Users userCreated = usersService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

     @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Users user = usersService.FindById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        List<Users> users = usersService.FindAll();
        return ResponseEntity.ok(users);
    }
}

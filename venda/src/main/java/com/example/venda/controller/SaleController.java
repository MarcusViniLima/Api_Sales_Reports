package com.example.venda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.venda.entities.Sale;
import com.example.venda.service.SaleService;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Sale sale) {
        saleService.save(sale);
        return ResponseEntity.status(HttpStatus.OK).body(sale);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<Sale> sales = saleService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Sale sale = saleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(sale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        saleService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Venda deletada com sucesso.");
    }

}
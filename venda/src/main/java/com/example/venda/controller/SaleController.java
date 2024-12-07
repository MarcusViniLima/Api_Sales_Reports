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

import com.example.venda.dto.SaleCreateDTO;
import com.example.venda.entities.Sale;
import com.example.venda.service.SaleService;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody SaleCreateDTO saleDto) {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.save(saleDto));
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<Sale> sales = saleService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(sales);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Object> findById(@PathVariable Long code) {
        Sale sale = saleService.findByCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(sale);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Object> delete(@PathVariable Long code) {
        saleService.delete(code);
        return ResponseEntity.status(HttpStatus.OK).body("Sucess to delete sale.");
    }
    @PostMapping("/register")
    public String registerSales() {
        try {
            saleService.registerSalesFromJson();
            return "Sales registered successfully!";
        } catch (Exception e) {
            return "Error registering clients: " + e.getMessage();
        }
    }

}

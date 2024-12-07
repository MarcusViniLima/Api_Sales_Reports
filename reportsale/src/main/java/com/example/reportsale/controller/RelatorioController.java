package com.example.reportsale.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.reportsale.entities.dto.ConsumoMedioDTO;
import com.example.reportsale.entities.dto.ProdutoComBaixoEstoqueDTO;
import com.example.reportsale.entities.dto.ProdutoMaisVendidoDTO;
import com.example.reportsale.entities.dto.ProdutoPorClienteDTO;
import com.example.reportsale.sevice.RelatorioService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    
    @GetMapping("/produtos-mais-vendidos")
    public ResponseEntity<List<ProdutoMaisVendidoDTO>> getProdutosMaisVendidos() {
        List<ProdutoMaisVendidoDTO> produtosMaisVendidos = relatorioService.gerarRelatorioProdutosMaisVendidos();
        return ResponseEntity.ok(produtosMaisVendidos);
    }

    
    @GetMapping("/produtos-por-cliente")
    public ResponseEntity<List<ProdutoPorClienteDTO>> getProdutoPorCliente() {
        List<ProdutoPorClienteDTO> produtoPorCliente = relatorioService.gerarRelatorioProdutoPorCliente();
        return ResponseEntity.ok(produtoPorCliente);
    }

    
    @GetMapping("/consumo-medio-cliente")
    public ResponseEntity<List<ConsumoMedioDTO>> getConsumoMedioCliente() {
        List<ConsumoMedioDTO> consumoMedioCliente = relatorioService.gerarRelatorioConsumoMedioCliente();
        return ResponseEntity.ok(consumoMedioCliente);
    }
    
    @GetMapping("/produtos-baixo-estoque")
    public ResponseEntity<List<ProdutoComBaixoEstoqueDTO>> getProdutosComBaixoEstoque() {
        List<ProdutoComBaixoEstoqueDTO> produtosComBaixoEstoque = relatorioService.gerarRelatorioProdutosComBaixoEstoque();
        return ResponseEntity.ok(produtosComBaixoEstoque);
    }

}

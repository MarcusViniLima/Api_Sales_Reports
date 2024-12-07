package com.example.reportsale.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reportsale.entities.dto.ConsumoMedioDTO;
import com.example.reportsale.entities.dto.ProdutoComBaixoEstoqueDTO;
import com.example.reportsale.entities.dto.ProdutoMaisVendidoDTO;
import com.example.reportsale.entities.dto.ProdutoPorClienteDTO;
import com.example.reportsale.repository.RelatorioRepository;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    
    public List<ProdutoMaisVendidoDTO> gerarRelatorioProdutosMaisVendidos() {
        return relatorioRepository.findProdutosMaisVendidos();
    }

    
    public List<ProdutoPorClienteDTO> gerarRelatorioProdutoPorCliente() {
        return relatorioRepository.findProdutoPorCliente();
    }

    
    public List<ConsumoMedioDTO> gerarRelatorioConsumoMedioCliente() {
        return relatorioRepository.findConsumoMedioCliente();
    }

    
    public List<ProdutoComBaixoEstoqueDTO> gerarRelatorioProdutosComBaixoEstoque() {
        return relatorioRepository.findProdutosComBaixoEstoque();
    }

}

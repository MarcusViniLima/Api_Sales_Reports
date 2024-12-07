package com.example.reportsale.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.reportsale.entities.dto.ConsumoMedioDTO;
import com.example.reportsale.entities.dto.ProdutoComBaixoEstoqueDTO;
import com.example.reportsale.entities.dto.ProdutoMaisVendidoDTO;
import com.example.reportsale.entities.dto.ProdutoPorClienteDTO;

@Repository
public class RelatorioRepository {
    
    @Autowired 
    private JdbcTemplate jdbcTemplate;

    
    public List<ProdutoMaisVendidoDTO> findProdutosMaisVendidos() {
        String sql = "SELECT p.name AS product_name, SUM(s.quantity_product) AS total_quantity_sold " +
                 "FROM sale s " +
                 "JOIN product p ON s.id_product = p.id " +
                 "GROUP BY p.name " +
                 "ORDER BY total_quantity_sold DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ProdutoMaisVendidoDTO(
                rs.getString("product_name"),
                rs.getInt("total_quantity_sold")
        ));
    }

    
    public List<ProdutoPorClienteDTO> findProdutoPorCliente() {
        String sql = "SELECT c.name AS client_name, p.name AS product_name, SUM(s.quantity_product) AS total_quantity_purchased " +
                     "FROM sale s " +
                     "JOIN client c ON s.id_client = c.id " +
                     "JOIN product p ON s.id_product = p.id " +
                     "GROUP BY c.name, p.name " +
                     "ORDER BY client_name, product_name";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ProdutoPorClienteDTO(
                rs.getString("client_name"),
                rs.getString("product_name"),
                rs.getInt("total_quantity_purchased")
        ));
    }

    
    public List<ConsumoMedioDTO> findConsumoMedioCliente() {
        String sql = "SELECT c.name AS client_name, AVG(s.quantity_product) AS avg_quantity_purchased " +
                     "FROM sale s " +
                     "JOIN client c ON s.id_client = c.id " +
                     "GROUP BY c.name " +
                     "ORDER BY client_name";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ConsumoMedioDTO(
                rs.getString("client_name"),
                rs.getDouble("avg_quantity_purchased")
        ));
    }

    
    public List<ProdutoComBaixoEstoqueDTO> findProdutosComBaixoEstoque() {
        String sql = "SELECT p.name AS product_name, p.quantity AS current_stock " +
                     "FROM product p " +
                     "WHERE p.quantity < 10 " +
                     "ORDER BY current_stock ASC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new ProdutoComBaixoEstoqueDTO(
                rs.getString("product_name"),
                rs.getInt("current_stock")
        ));
    }

    

}

package com.fergabgabsam.atividadefinal.gestao_espaco.repository;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.Equipamento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class EquipamentoRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public EquipamentoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private final RowMapper<Equipamento> rowMapper = (rs, rowNum) -> {
        Equipamento equipamento = new Equipamento();
        equipamento.setId(rs.getInt("id"));
        equipamento.setNomeEquipamento(rs.getString("nome_equipamento"));
        equipamento.setDescricao(rs.getString("descricao"));
        return equipamento;
    };
    
    public List<Equipamento> findAll() {
        return jdbcTemplate.query("SELECT * FROM Equipamentos", rowMapper);
    }
    
    public Optional<Equipamento> findById(Integer id) {
        List<Equipamento> equipamentos = jdbcTemplate.query("SELECT * FROM Equipamentos WHERE id = ?", rowMapper, id);
        return equipamentos.isEmpty() ? Optional.empty() : Optional.of(equipamentos.get(0));
    }
    
    public List<Equipamento> findByEspacoId(Integer espacoId) {
        String sql = "SELECT e.* FROM Equipamentos e " +
                     "JOIN Espacos_Equipamentos ee ON e.id = ee.id_equipamento " +
                     "WHERE ee.id_espaco = ?";
        return jdbcTemplate.query(sql, rowMapper, espacoId);
    }
}

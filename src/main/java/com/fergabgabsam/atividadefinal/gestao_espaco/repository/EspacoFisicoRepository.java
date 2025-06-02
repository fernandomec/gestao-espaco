package com.fergabgabsam.atividadefinal.gestao_espaco.repository;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.EspacoFisico;
import com.fergabgabsam.atividadefinal.gestao_espaco.model.Equipamento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EspacoFisicoRepository {
    
    private final JdbcTemplate jdbcTemplate;
    private final EquipamentoRepository equipamentoRepository;
    
    public EspacoFisicoRepository(JdbcTemplate jdbcTemplate, EquipamentoRepository equipamentoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.equipamentoRepository = equipamentoRepository;
    }
    
    private final RowMapper<EspacoFisico> rowMapper = (rs, rowNum) -> {
        EspacoFisico espacoFisico = new EspacoFisico();
        espacoFisico.setId(rs.getInt("id"));
        espacoFisico.setNomeEspaco(rs.getString("nome_espaco"));
        espacoFisico.setTipoEspaco(rs.getString("tipo_espaco"));
        espacoFisico.setMetragem(rs.getBigDecimal("metragem"));
        espacoFisico.setCapacidade(rs.getInt("capacidade"));
        espacoFisico.setLocalizacao(rs.getString("localizacao"));
        espacoFisico.setAtivo(rs.getBoolean("ativo"));
        espacoFisico.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
        return espacoFisico;
    };
    
    public List<EspacoFisico> findAll() {
        List<EspacoFisico> espacos = jdbcTemplate.query("SELECT * FROM EspacosFisicos", rowMapper);
        loadEquipamentos(espacos);
        return espacos;
    }
    
    public List<EspacoFisico> findAllAtivos() {
        List<EspacoFisico> espacos = jdbcTemplate.query("SELECT * FROM EspacosFisicos WHERE ativo = TRUE", rowMapper);
        loadEquipamentos(espacos);
        return espacos;
    }
    
    public Optional<EspacoFisico> findById(Integer id) {
        List<EspacoFisico> espacos = jdbcTemplate.query("SELECT * FROM EspacosFisicos WHERE id = ?", rowMapper, id);
        if (espacos.isEmpty()) {
            return Optional.empty();
        }
        
        EspacoFisico espaco = espacos.get(0);
        loadEquipamentos(List.of(espaco));
        return Optional.of(espaco);
    }
    
    private void loadEquipamentos(List<EspacoFisico> espacos) {
        if (espacos.isEmpty()) {
            return;
        }
        
        Map<Integer, EspacoFisico> espacoMap = new HashMap<>();
        for (EspacoFisico espaco : espacos) {
            espacoMap.put(espaco.getId(), espaco);
            espaco.setEquipamentos(new ArrayList<>());
        }
        
        String sql = "SELECT ee.id_espaco, e.* FROM Equipamentos e " +
                     "JOIN Espacos_Equipamentos ee ON e.id = ee.id_equipamento " +
                     "WHERE ee.id_espaco IN (" + 
                     String.join(",", espacoMap.keySet().stream().map(Object::toString).toArray(String[]::new)) + ")";
        
        jdbcTemplate.query(sql, (rs) -> {
            int espacoId = rs.getInt("id_espaco");
            Equipamento equipamento = new Equipamento();
            equipamento.setId(rs.getInt("id"));
            equipamento.setNomeEquipamento(rs.getString("nome_equipamento"));
            equipamento.setDescricao(rs.getString("descricao"));
            
            EspacoFisico espaco = espacoMap.get(espacoId);
            if (espaco != null) {
                espaco.getEquipamentos().add(equipamento);
            }
        });
    }
}

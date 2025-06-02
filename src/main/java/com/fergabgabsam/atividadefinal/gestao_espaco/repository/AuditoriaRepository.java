package com.fergabgabsam.atividadefinal.gestao_espaco.repository;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.Auditoria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class AuditoriaRepository {
    
    private final JdbcTemplate jdbcTemplate;
    private final UsuarioRepository usuarioRepository;
    private final RowMapper<Auditoria> rowMapper;
    
    public AuditoriaRepository(JdbcTemplate jdbcTemplate, UsuarioRepository usuarioRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.usuarioRepository = usuarioRepository;
        this.rowMapper = (rs, rowNum) -> {
            Auditoria auditoria = new Auditoria();
            auditoria.setId(rs.getInt("id"));
            auditoria.setIdUsuario(rs.getInt("id_usuario"));
            auditoria.setDataHoraAcao(rs.getTimestamp("data_hora_acao").toLocalDateTime());
            auditoria.setTipoAcao(rs.getString("tipo_acao"));
            auditoria.setDetalhesAcao(rs.getString("detalhes_acao"));
            
            usuarioRepository.findById(auditoria.getIdUsuario())
                    .ifPresent(auditoria::setUsuario);
            
            return auditoria;
        };
    }
    
    public List<Auditoria> findAll() {
        return jdbcTemplate.query("SELECT * FROM Auditoria ORDER BY data_hora_acao DESC", rowMapper);
    }
    
    public Integer registrarAcao(Auditoria auditoria) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO Auditoria (id_usuario, tipo_acao, detalhes_acao) " +
                    "VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, auditoria.getIdUsuario());
            ps.setString(2, auditoria.getTipoAcao());
            ps.setString(3, auditoria.getDetalhesAcao());
            
            return ps;
        }, keyHolder);
        
        return (Integer) keyHolder.getKeys().get("id");
    }
}

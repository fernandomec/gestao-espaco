package com.fergabgabsam.atividadefinal.gestao_espaco.repository;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.HistoricoAvaliacao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class HistoricoAvaliacaoRepository {
    
    private final JdbcTemplate jdbcTemplate;
    private final UsuarioRepository usuarioRepository;
    private final SolicitacaoReservaRepository solicitacaoRepository;
    
    public HistoricoAvaliacaoRepository(JdbcTemplate jdbcTemplate, 
                                        UsuarioRepository usuarioRepository, 
                                        SolicitacaoReservaRepository solicitacaoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.usuarioRepository = usuarioRepository;
        this.solicitacaoRepository = solicitacaoRepository;
    }
    
    private RowMapper<HistoricoAvaliacao> getRowMapper() {
        return (rs, rowNum) -> {
            HistoricoAvaliacao avaliacao = new HistoricoAvaliacao();
            avaliacao.setId(rs.getInt("id"));
            avaliacao.setIdSolicitacao(rs.getInt("id_solicitacao"));
            avaliacao.setIdGestor(rs.getInt("id_gestor"));
            avaliacao.setDataAvaliacao(rs.getTimestamp("data_avaliacao").toLocalDateTime());
            avaliacao.setNovoStatus(rs.getString("novo_status"));
            avaliacao.setJustificativa(rs.getString("justificativa"));
            
            usuarioRepository.findById(avaliacao.getIdGestor())
                    .ifPresent(avaliacao::setGestor);
            
            solicitacaoRepository.findById(avaliacao.getIdSolicitacao())
                    .ifPresent(avaliacao::setSolicitacao);
            
            return avaliacao;
        };
    }
    
    public List<HistoricoAvaliacao> findAll() {
        return jdbcTemplate.query("SELECT * FROM HistoricoAvaliacoes", getRowMapper());
    }
    
    public List<HistoricoAvaliacao> findBySolicitacaoId(Integer solicitacaoId) {
        return jdbcTemplate.query("SELECT * FROM HistoricoAvaliacoes WHERE id_solicitacao = ?", getRowMapper(), solicitacaoId);
    }
    
    public Integer save(HistoricoAvaliacao avaliacao) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO HistoricoAvaliacoes (id_solicitacao, id_gestor, novo_status, justificativa) " + "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, avaliacao.getIdSolicitacao());
            ps.setInt(2, avaliacao.getIdGestor());
            ps.setString(3, avaliacao.getNovoStatus());
            ps.setString(4, avaliacao.getJustificativa());
            
            return ps;
        }, keyHolder);
        
        return (Integer) keyHolder.getKeys().get("id");
    }
}

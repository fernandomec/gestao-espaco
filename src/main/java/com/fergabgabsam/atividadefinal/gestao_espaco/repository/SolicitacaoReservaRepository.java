package com.fergabgabsam.atividadefinal.gestao_espaco.repository;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.SolicitacaoReserva;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class SolicitacaoReservaRepository {
    
    private final JdbcTemplate jdbcTemplate;
    private final UsuarioRepository usuarioRepository;
    private final EspacoFisicoRepository espacoFisicoRepository;
    
    public SolicitacaoReservaRepository(JdbcTemplate jdbcTemplate, UsuarioRepository usuarioRepository, EspacoFisicoRepository espacoFisicoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.usuarioRepository = usuarioRepository;
        this.espacoFisicoRepository = espacoFisicoRepository;
    }
    
    private RowMapper<SolicitacaoReserva> getRowMapper() {
        return (rs, rowNum) -> {
            SolicitacaoReserva solicitacao = new SolicitacaoReserva();
            solicitacao.setId(rs.getInt("id"));
            solicitacao.setIdSolicitante(rs.getInt("id_solicitante"));
            solicitacao.setIdEspaco(rs.getInt("id_espaco"));
            solicitacao.setDataSolicitacao(rs.getTimestamp("data_solicitacao").toLocalDateTime());
            solicitacao.setDataReserva(rs.getDate("data_reserva").toLocalDate());
            solicitacao.setHoraInicioReserva(rs.getTime("hora_inicio_reserva").toLocalTime());
            solicitacao.setHoraFimReserva(rs.getTime("hora_fim_reserva").toLocalTime());
            solicitacao.setStatusSolicitacao(rs.getString("status_solicitacao"));
            
            usuarioRepository.findById(solicitacao.getIdSolicitante())
                    .ifPresent(solicitacao::setSolicitante);
            
            espacoFisicoRepository.findById(solicitacao.getIdEspaco())
                    .ifPresent(solicitacao::setEspacoFisico);
            
            return solicitacao;
        };
    }
    
    public List<SolicitacaoReserva> findAll() {
        return jdbcTemplate.query("SELECT * FROM SolicitacoesReserva", getRowMapper());
    }
    
    public Optional<SolicitacaoReserva> findById(Integer id) {
        List<SolicitacaoReserva> solicitacoes = jdbcTemplate.query(
                "SELECT * FROM SolicitacoesReserva WHERE id = ?", getRowMapper(), id);
        return solicitacoes.isEmpty() ? Optional.empty() : Optional.of(solicitacoes.get(0));
    }
    
    public List<SolicitacaoReserva> findBySolicitanteId(Integer solicitanteId) {
        return jdbcTemplate.query(
                "SELECT * FROM SolicitacoesReserva WHERE id_solicitante = ?", getRowMapper(), solicitanteId);
    }
    
    public List<SolicitacaoReserva> findByStatus(String status) {
        return jdbcTemplate.query(
                "SELECT * FROM SolicitacoesReserva WHERE status_solicitacao = ?", getRowMapper(), status);
    }
    
    public boolean verificarDisponibilidade(Integer espacoId, java.sql.Date dataReserva, java.sql.Time horaInicio, java.sql.Time horaFim) {
        String sql = "SELECT COUNT(*) FROM SolicitacoesReserva " +
                     "WHERE id_espaco = ? AND data_reserva = ? " +
                     "AND status_solicitacao = 'APROVADA' " +
                     "AND ((hora_inicio_reserva <= ? AND hora_fim_reserva > ?) " +
                     "OR (hora_inicio_reserva < ? AND hora_fim_reserva >= ?))";
        
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, espacoId, dataReserva, horaFim, horaInicio, horaFim, horaInicio);
        return count != null && count == 0;
    }
    
    public Integer save(SolicitacaoReserva solicitacao) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO SolicitacoesReserva (id_solicitante, id_espaco, data_reserva, " +
                    "hora_inicio_reserva, hora_fim_reserva, status_solicitacao) " +
                    "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, solicitacao.getIdSolicitante());
            ps.setInt(2, solicitacao.getIdEspaco());
            ps.setDate(3, java.sql.Date.valueOf(solicitacao.getDataReserva()));
            ps.setTime(4, java.sql.Time.valueOf(solicitacao.getHoraInicioReserva()));
            ps.setTime(5, java.sql.Time.valueOf(solicitacao.getHoraFimReserva()));
            ps.setString(6, solicitacao.getStatusSolicitacao());
            
            return ps;
        }, keyHolder);
        
        return (Integer) keyHolder.getKeys().get("id");
    }
    
    public void updateStatus(Integer id, String novoStatus) {
        jdbcTemplate.update(
                "UPDATE SolicitacoesReserva SET status_solicitacao = ? WHERE id = ?",
                novoStatus, id);
    }
}

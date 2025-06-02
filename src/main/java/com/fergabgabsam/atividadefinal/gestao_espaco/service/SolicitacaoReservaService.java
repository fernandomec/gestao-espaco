package com.fergabgabsam.atividadefinal.gestao_espaco.service;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.Auditoria;
import com.fergabgabsam.atividadefinal.gestao_espaco.model.HistoricoAvaliacao;
import com.fergabgabsam.atividadefinal.gestao_espaco.model.SolicitacaoReserva;
import com.fergabgabsam.atividadefinal.gestao_espaco.repository.AuditoriaRepository;
import com.fergabgabsam.atividadefinal.gestao_espaco.repository.HistoricoAvaliacaoRepository;
import com.fergabgabsam.atividadefinal.gestao_espaco.repository.SolicitacaoReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitacaoReservaService {
    
    private final SolicitacaoReservaRepository solicitacaoRepository;
    private final HistoricoAvaliacaoRepository historicoRepository;
    private final AuditoriaRepository auditoriaRepository;
    
    public SolicitacaoReservaService(SolicitacaoReservaRepository solicitacaoRepository, HistoricoAvaliacaoRepository historicoRepository, AuditoriaRepository auditoriaRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.historicoRepository = historicoRepository;
        this.auditoriaRepository = auditoriaRepository;
    }
    
    public List<SolicitacaoReserva> findAll() {
        return solicitacaoRepository.findAll();
    }
    
    public Optional<SolicitacaoReserva> findById(Integer id) {
        return solicitacaoRepository.findById(id);
    }
    
    public List<SolicitacaoReserva> findBySolicitanteId(Integer solicitanteId) {
        return solicitacaoRepository.findBySolicitanteId(solicitanteId);
    }
    
    public List<SolicitacaoReserva> findByStatus(String status) {
        return solicitacaoRepository.findByStatus(status);
    }
    
    public boolean verificarDisponibilidade(Integer espacoId, LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim) {
        return solicitacaoRepository.verificarDisponibilidade(
            espacoId, Date.valueOf(dataReserva), Time.valueOf(horaInicio), Time.valueOf(horaFim)
        );
    }
    
    @Transactional
    public Integer criarSolicitacao(SolicitacaoReserva solicitacao) {
        solicitacao.setStatusSolicitacao("PENDENTE");
        Integer id = solicitacaoRepository.save(solicitacao);
        
        Auditoria auditoria = new Auditoria();
        auditoria.setIdUsuario(solicitacao.getIdSolicitante());
        auditoria.setTipoAcao("CRIAR_SOLICITACAO");
        auditoria.setDetalhesAcao("Criação de solicitação para o espaço " + solicitacao.getIdEspaco() + " na data " + solicitacao.getDataReserva());
        auditoriaRepository.registrarAcao(auditoria);
        
        return id;
    }
    
    @Transactional
    public void avaliarSolicitacao(Integer solicitacaoId, Integer gestorId, String novoStatus, String justificativa) {
        Optional<SolicitacaoReserva> solicitacaoOpt = solicitacaoRepository.findById(solicitacaoId);
        
        if (solicitacaoOpt.isPresent()) {
            SolicitacaoReserva solicitacao = solicitacaoOpt.get();
            
            if ("APROVADA".equals(novoStatus)) {
                boolean disponivel = verificarDisponibilidade(
                        solicitacao.getIdEspaco(),
                        solicitacao.getDataReserva(),
                        solicitacao.getHoraInicioReserva(),
                        solicitacao.getHoraFimReserva()
                );
                
                if (!disponivel) {
                    throw new RuntimeException("Espaço não disponível no horário solicitado");
                }
            }
            
            solicitacaoRepository.updateStatus(solicitacaoId, novoStatus);
            
            HistoricoAvaliacao historico = new HistoricoAvaliacao();
            historico.setIdSolicitacao(solicitacaoId);
            historico.setIdGestor(gestorId);
            historico.setNovoStatus(novoStatus);
            historico.setJustificativa(justificativa);
            historicoRepository.save(historico);
            
            Auditoria auditoria = new Auditoria();
            auditoria.setIdUsuario(gestorId);
            auditoria.setTipoAcao("AVALIAR_SOLICITACAO");
            auditoria.setDetalhesAcao("Avaliação da solicitação " + solicitacaoId + " com status " + novoStatus);
            auditoriaRepository.registrarAcao(auditoria);
        } else {
            throw new RuntimeException("Solicitação não encontrada");
        }
    }
    
    public List<HistoricoAvaliacao> findHistoricoAvaliacoes() {
        return historicoRepository.findAll();
    }
    
    public List<HistoricoAvaliacao> findHistoricoBySolicitacaoId(Integer solicitacaoId) {
        return historicoRepository.findBySolicitacaoId(solicitacaoId);
    }
}
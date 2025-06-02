package com.fergabgabsam.atividadefinal.gestao_espaco.model;

import java.time.LocalDateTime;

public class HistoricoAvaliacao {
    private Integer id;
    private Integer idSolicitacao;
    private Integer idGestor;
    private LocalDateTime dataAvaliacao;
    private String novoStatus;
    private String justificativa;
    
    private Usuario gestor;
    private SolicitacaoReserva solicitacao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(Integer idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    public Integer getIdGestor() {
        return idGestor;
    }

    public void setIdGestor(Integer idGestor) {
        this.idGestor = idGestor;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public String getNovoStatus() {
        return novoStatus;
    }

    public void setNovoStatus(String novoStatus) {
        this.novoStatus = novoStatus;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public Usuario getGestor() {
        return gestor;
    }

    public void setGestor(Usuario gestor) {
        this.gestor = gestor;
    }

    public SolicitacaoReserva getSolicitacao() {
        return solicitacao;
    }

    public void setSolicitacao(SolicitacaoReserva solicitacao) {
        this.solicitacao = solicitacao;
    }
}

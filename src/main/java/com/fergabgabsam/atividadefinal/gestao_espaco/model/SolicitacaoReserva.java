package com.fergabgabsam.atividadefinal.gestao_espaco.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class SolicitacaoReserva {
    private Integer id;
    private Integer idSolicitante;
    private Integer idEspaco;
    private LocalDateTime dataSolicitacao;
    private LocalDate dataReserva;
    private LocalTime horaInicioReserva;
    private LocalTime horaFimReserva;
    private String statusSolicitacao;
    
    private Usuario solicitante;
    private EspacoFisico espacoFisico;
    private List<HistoricoAvaliacao> historicoAvaliacoes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Integer idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public Integer getIdEspaco() {
        return idEspaco;
    }

    public void setIdEspaco(Integer idEspaco) {
        this.idEspaco = idEspaco;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    public LocalTime getHoraInicioReserva() {
        return horaInicioReserva;
    }

    public void setHoraInicioReserva(LocalTime horaInicioReserva) {
        this.horaInicioReserva = horaInicioReserva;
    }

    public LocalTime getHoraFimReserva() {
        return horaFimReserva;
    }

    public void setHoraFimReserva(LocalTime horaFimReserva) {
        this.horaFimReserva = horaFimReserva;
    }

    public String getStatusSolicitacao() {
        return statusSolicitacao;
    }

    public void setStatusSolicitacao(String statusSolicitacao) {
        this.statusSolicitacao = statusSolicitacao;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    public EspacoFisico getEspacoFisico() {
        return espacoFisico;
    }

    public void setEspacoFisico(EspacoFisico espacoFisico) {
        this.espacoFisico = espacoFisico;
    }

    public List<HistoricoAvaliacao> getHistoricoAvaliacoes() {
        return historicoAvaliacoes;
    }

    public void setHistoricoAvaliacoes(List<HistoricoAvaliacao> historicoAvaliacoes) {
        this.historicoAvaliacoes = historicoAvaliacoes;
    }
}

package com.fergabgabsam.atividadefinal.gestao_espaco.model;

import java.time.LocalDateTime;

public class Auditoria {
    private Integer id;
    private Integer idUsuario;
    private LocalDateTime dataHoraAcao;
    private String tipoAcao;
    private String detalhesAcao;
    
    private Usuario usuario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getDataHoraAcao() {
        return dataHoraAcao;
    }

    public void setDataHoraAcao(LocalDateTime dataHoraAcao) {
        this.dataHoraAcao = dataHoraAcao;
    }

    public String getTipoAcao() {
        return tipoAcao;
    }

    public void setTipoAcao(String tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    public String getDetalhesAcao() {
        return detalhesAcao;
    }

    public void setDetalhesAcao(String detalhesAcao) {
        this.detalhesAcao = detalhesAcao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

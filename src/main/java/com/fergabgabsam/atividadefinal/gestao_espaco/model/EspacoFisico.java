package com.fergabgabsam.atividadefinal.gestao_espaco.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class EspacoFisico {
    private Integer id;
    private String nomeEspaco;
    private String tipoEspaco;
    private BigDecimal metragem;
    private Integer capacidade;
    private String localizacao;
    private boolean ativo;
    private LocalDateTime dataCadastro;
    private List<Equipamento> equipamentos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeEspaco() {
        return nomeEspaco;
    }

    public void setNomeEspaco(String nomeEspaco) {
        this.nomeEspaco = nomeEspaco;
    }

    public String getTipoEspaco() {
        return tipoEspaco;
    }

    public void setTipoEspaco(String tipoEspaco) {
        this.tipoEspaco = tipoEspaco;
    }

    public BigDecimal getMetragem() {
        return metragem;
    }

    public void setMetragem(BigDecimal metragem) {
        this.metragem = metragem;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }
}

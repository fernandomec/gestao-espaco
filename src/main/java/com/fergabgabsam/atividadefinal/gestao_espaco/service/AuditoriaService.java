package com.fergabgabsam.atividadefinal.gestao_espaco.service;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.Auditoria;
import com.fergabgabsam.atividadefinal.gestao_espaco.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriaService {
    
    private final AuditoriaRepository auditoriaRepository;
    
    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }
    
    public List<Auditoria> findAll() {
        return auditoriaRepository.findAll();
    }
    
    public void registrarAcao(Integer usuarioId, String tipoAcao, String detalhesAcao) {
        Auditoria auditoria = new Auditoria();
        auditoria.setIdUsuario(usuarioId);
        auditoria.setTipoAcao(tipoAcao);
        auditoria.setDetalhesAcao(detalhesAcao);
        auditoriaRepository.registrarAcao(auditoria);
    }
}

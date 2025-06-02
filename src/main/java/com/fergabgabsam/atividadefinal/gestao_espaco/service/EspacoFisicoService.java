package com.fergabgabsam.atividadefinal.gestao_espaco.service;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.EspacoFisico;
import com.fergabgabsam.atividadefinal.gestao_espaco.repository.EspacoFisicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspacoFisicoService {
    
    private final EspacoFisicoRepository espacoFisicoRepository;
    
    public EspacoFisicoService(EspacoFisicoRepository espacoFisicoRepository) {
        this.espacoFisicoRepository = espacoFisicoRepository;
    }
    
    public List<EspacoFisico> findAll() {
        return espacoFisicoRepository.findAll();
    }
    
    public List<EspacoFisico> findAllAtivos() {
        return espacoFisicoRepository.findAllAtivos();
    }
    
    public Optional<EspacoFisico> findById(Integer id) {
        return espacoFisicoRepository.findById(id);
    }
}

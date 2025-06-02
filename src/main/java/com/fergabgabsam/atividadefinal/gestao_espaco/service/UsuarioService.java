package com.fergabgabsam.atividadefinal.gestao_espaco.service;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.Usuario;
import com.fergabgabsam.atividadefinal.gestao_espaco.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }
    
    public Optional<Usuario> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
    
    public List<Usuario> findAllSolicitantes() {
        return usuarioRepository.findAllSolicitantes();
    }
    
    public List<Usuario> findAllGestores() {
        return usuarioRepository.findAllGestores();
    }
    
    public boolean autenticar(String login, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByLogin(login);
        return usuarioOpt.isPresent() && usuarioOpt.get().getSenha().equals(senha);
    }
}

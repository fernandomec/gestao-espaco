package com.fergabgabsam.atividadefinal.gestao_espaco.controller;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.Auditoria;
import com.fergabgabsam.atividadefinal.gestao_espaco.model.Usuario;
import com.fergabgabsam.atividadefinal.gestao_espaco.service.AuditoriaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/auditoria")
public class AuditoriaController {
    
    private final AuditoriaService auditoriaService;
    
    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }
    
    @GetMapping
    public String listarRegistros(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"GESTOR".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        List<Auditoria> registros = auditoriaService.findAll();
        model.addAttribute("registros", registros);
        return "auditoria/listar";
    }
}

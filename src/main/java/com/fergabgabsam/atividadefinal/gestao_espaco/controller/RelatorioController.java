package com.fergabgabsam.atividadefinal.gestao_espaco.controller;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.EspacoFisico;
import com.fergabgabsam.atividadefinal.gestao_espaco.model.SolicitacaoReserva;
import com.fergabgabsam.atividadefinal.gestao_espaco.model.Usuario;
import com.fergabgabsam.atividadefinal.gestao_espaco.service.EspacoFisicoService;
import com.fergabgabsam.atividadefinal.gestao_espaco.service.SolicitacaoReservaService;
import com.fergabgabsam.atividadefinal.gestao_espaco.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {
    
    private final UsuarioService usuarioService;
    private final EspacoFisicoService espacoService;
    private final SolicitacaoReservaService solicitacaoService;
    
    public RelatorioController(UsuarioService usuarioService, 
                              EspacoFisicoService espacoService, 
                              SolicitacaoReservaService solicitacaoService) {
        this.usuarioService = usuarioService;
        this.espacoService = espacoService;
        this.solicitacaoService = solicitacaoService;
    }
    
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"GESTOR".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        List<Usuario> solicitantes = usuarioService.findAllSolicitantes();
        List<Usuario> gestores = usuarioService.findAllGestores();
        
        model.addAttribute("solicitantes", solicitantes);
        model.addAttribute("gestores", gestores);
        return "relatorios/usuarios";
    }
    
    @GetMapping("/espacos")
    public String listarEspacos(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"GESTOR".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        List<EspacoFisico> espacos = espacoService.findAll();
        model.addAttribute("espacos", espacos);
        return "relatorios/espacos";
    }
    
    @GetMapping("/solicitacoes")
    public String listarSolicitacoes(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"GESTOR".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        List<SolicitacaoReserva> solicitacoes = solicitacaoService.findAll();
        model.addAttribute("solicitacoes", solicitacoes);
        return "relatorios/solicitacoes";
    }
}

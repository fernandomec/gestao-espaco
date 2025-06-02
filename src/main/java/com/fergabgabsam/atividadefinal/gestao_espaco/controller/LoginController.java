package com.fergabgabsam.atividadefinal.gestao_espaco.controller;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.Usuario;
import com.fergabgabsam.atividadefinal.gestao_espaco.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {
    
    private final UsuarioService usuarioService;
    
    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String login, 
                        @RequestParam String senha, 
                        HttpSession session,
                        Model model) {
        
        Optional<Usuario> usuarioOpt = usuarioService.findByLogin(login);
        
        if (usuarioOpt.isPresent() && usuarioOpt.get().getSenha().equals(senha)) {
            Usuario usuario = usuarioOpt.get();
            session.setAttribute("usuarioLogado", usuario);
            
            if ("GESTOR".equals(usuario.getTipoUsuario())) {
                return "redirect:/gestor/solicitacoes";
            } else {
                return "redirect:/solicitante/espacos";
            }
        }
        
        model.addAttribute("erro", "Login ou senha inválidos");
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

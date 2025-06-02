package com.fergabgabsam.atividadefinal.gestao_espaco.controller;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.HistoricoAvaliacao;
import com.fergabgabsam.atividadefinal.gestao_espaco.model.SolicitacaoReserva;
import com.fergabgabsam.atividadefinal.gestao_espaco.model.Usuario;
import com.fergabgabsam.atividadefinal.gestao_espaco.service.SolicitacaoReservaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gestor")
public class GestorController {
    
    private final SolicitacaoReservaService solicitacaoService;
    
    public GestorController(SolicitacaoReservaService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }
    
    @GetMapping("/solicitacoes")
    public String listarSolicitacoesPendentes(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"GESTOR".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        List<SolicitacaoReserva> solicitacoes = solicitacaoService.findByStatus("PENDENTE");
        model.addAttribute("solicitacoes", solicitacoes);
        return "gestor/solicitacoesPendentes";
    }
    
    @GetMapping("/solicitacoes/aprovadas")
    public String listarSolicitacoesAprovadas(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"GESTOR".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        List<SolicitacaoReserva> solicitacoes = solicitacaoService.findByStatus("APROVADA");
        model.addAttribute("solicitacoes", solicitacoes);
        return "gestor/solicitacoesAprovadas";
    }
    
    @GetMapping("/solicitacoes/rejeitadas")
    public String listarSolicitacoesRejeitadas(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"GESTOR".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        List<SolicitacaoReserva> solicitacoes = solicitacaoService.findByStatus("REJEITADA");
        
        for (SolicitacaoReserva solicitacao : solicitacoes) {
            List<HistoricoAvaliacao> historico = solicitacaoService.findHistoricoBySolicitacaoId(solicitacao.getId());
            
            if (historico != null && !historico.isEmpty()) {
                solicitacao.setHistoricoAvaliacoes(historico);
            } else {
                solicitacao.setHistoricoAvaliacoes(List.of());
            }
        }
        
        model.addAttribute("solicitacoes", solicitacoes);
        return "gestor/solicitacoesRejeitadas";
    }
    
    @GetMapping("/avaliar/{id}")
    public String formAvaliarSolicitacao(@PathVariable Integer id, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"GESTOR".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        Optional<SolicitacaoReserva> solicitacaoOpt = solicitacaoService.findById(id);
        if (solicitacaoOpt.isPresent()) {
            SolicitacaoReserva solicitacao = solicitacaoOpt.get();
            
            if (!"PENDENTE".equals(solicitacao.getStatusSolicitacao())) {
                return "redirect:/gestor/solicitacoes";
            }
            
            model.addAttribute("solicitacao", solicitacao);
            return "gestor/avaliarSolicitacao";
        }
        
        return "redirect:/gestor/solicitacoes";
    }
    
    @PostMapping("/avaliar")
    public String avaliarSolicitacao(
            @RequestParam Integer idSolicitacao,
            @RequestParam String novoStatus,
            @RequestParam(required = false) String justificativa,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"GESTOR".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        if ("REJEITADA".equals(novoStatus) && (justificativa == null || justificativa.trim().isEmpty())) {
            redirectAttributes.addFlashAttribute("erro", "É necessário fornecer uma justificativa para rejeitar a solicitação");
            return "redirect:/gestor/avaliar/" + idSolicitacao;
        }
        
        try {
            solicitacaoService.avaliarSolicitacao(idSolicitacao, usuario.getId(), novoStatus, justificativa);
            redirectAttributes.addFlashAttribute("sucesso", "Solicitação avaliada com sucesso");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao avaliar solicitação: " + e.getMessage());
        }
        
        return "redirect:/gestor/solicitacoes";
    }
    
    @GetMapping("/historico")
    public String historicoAvaliacoes(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"GESTOR".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        List<HistoricoAvaliacao> historico = solicitacaoService.findHistoricoAvaliacoes();
        model.addAttribute("historico", historico);
        return "gestor/historicoAvaliacoes";
    }
}

package com.fergabgabsam.atividadefinal.gestao_espaco.controller;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.EspacoFisico;
import com.fergabgabsam.atividadefinal.gestao_espaco.model.SolicitacaoReserva;
import com.fergabgabsam.atividadefinal.gestao_espaco.model.Usuario;
import com.fergabgabsam.atividadefinal.gestao_espaco.service.EspacoFisicoService;
import com.fergabgabsam.atividadefinal.gestao_espaco.service.SolicitacaoReservaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/solicitante")
public class SolicitanteController {
    
    private final EspacoFisicoService espacoService;
    private final SolicitacaoReservaService solicitacaoService;
    
    public SolicitanteController(EspacoFisicoService espacoService, 
                                SolicitacaoReservaService solicitacaoService) {
        this.espacoService = espacoService;
        this.solicitacaoService = solicitacaoService;
    }
    
    @GetMapping("/espacos")
    public String listarEspacos(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"SOLICITANTE".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        List<EspacoFisico> espacos = espacoService.findAllAtivos();
        model.addAttribute("espacos", espacos);
        return "solicitante/espacos";
    }
    
    @GetMapping("/espaco/{id}")
    public String verEspaco(@PathVariable Integer id, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"SOLICITANTE".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        Optional<EspacoFisico> espacoOpt = espacoService.findById(id);
        if (espacoOpt.isPresent()) {
            model.addAttribute("espaco", espacoOpt.get());
            return "solicitante/verEspaco";
        }
        
        return "redirect:/solicitante/espacos";
    }
    
    @GetMapping("/solicitar/{id}")
    public String formularioSolicitacao(@PathVariable Integer id, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"SOLICITANTE".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        Optional<EspacoFisico> espacoOpt = espacoService.findById(id);
        if (espacoOpt.isPresent()) {
            model.addAttribute("espaco", espacoOpt.get());
            model.addAttribute("dataMinima", LocalDate.now().plusDays(1));
            return "solicitante/solicitarReserva";
        }
        
        return "redirect:/solicitante/espacos";
    }
    
    @PostMapping("/solicitar")
    public String criarSolicitacao(
            @RequestParam Integer idEspaco,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataReserva,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaFim,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"SOLICITANTE".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        if (dataReserva.isBefore(LocalDate.now())) {
            redirectAttributes.addFlashAttribute("erro", "Data de reserva inválida");
            return "redirect:/solicitante/solicitar/" + idEspaco;
        }
        
        if (horaInicio.isAfter(horaFim) || horaInicio.equals(horaFim)) {
            redirectAttributes.addFlashAttribute("erro", "Horário de início deve ser anterior ao horário de fim");
            return "redirect:/solicitante/solicitar/" + idEspaco;
        }
        
        SolicitacaoReserva solicitacao = new SolicitacaoReserva();
        solicitacao.setIdSolicitante(usuario.getId());
        solicitacao.setIdEspaco(idEspaco);
        solicitacao.setDataReserva(dataReserva);
        solicitacao.setHoraInicioReserva(horaInicio);
        solicitacao.setHoraFimReserva(horaFim);
        
        try {
            solicitacaoService.criarSolicitacao(solicitacao);
            redirectAttributes.addFlashAttribute("sucesso", "Solicitação criada com sucesso");
            return "redirect:/solicitante/minhas-solicitacoes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao criar solicitação: " + e.getMessage());
            return "redirect:/solicitante/solicitar/" + idEspaco;
        }
    }
    
    @GetMapping("/minhas-solicitacoes")
    public String minhasSolicitacoes(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || !"SOLICITANTE".equals(usuario.getTipoUsuario())) {
            return "redirect:/login";
        }
        
        List<SolicitacaoReserva> solicitacoes = solicitacaoService.findBySolicitanteId(usuario.getId());
        model.addAttribute("solicitacoes", solicitacoes);
        return "solicitante/minhasSolicitacoes";
    }
}

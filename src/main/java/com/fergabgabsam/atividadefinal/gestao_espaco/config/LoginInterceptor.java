package com.fergabgabsam.atividadefinal.gestao_espaco.config;

import com.fergabgabsam.atividadefinal.gestao_espaco.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        
        if (usuario == null) {
            response.sendRedirect("/login");
            return false;
        }
        
        String path = request.getRequestURI();
        
        if (path.startsWith("/solicitante/") && !"SOLICITANTE".equals(usuario.getTipoUsuario())) {
            response.sendRedirect("/login");
            return false;
        }
        
        if ((path.startsWith("/gestor/") || path.startsWith("/relatorios/") || path.startsWith("/auditoria/")) 
                && !"GESTOR".equals(usuario.getTipoUsuario())) {
            response.sendRedirect("/login");
            return false;
        }
        
        return true;
    }
}

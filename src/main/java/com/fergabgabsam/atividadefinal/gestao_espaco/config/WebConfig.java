package com.fergabgabsam.atividadefinal.gestao_espaco.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/solicitante/**", "/gestor/**", "/relatorios/**", "/auditoria/**")
                .excludePathPatterns("/login", "/logout", "/css/**", "/js/**", "/images/**");
    }
}

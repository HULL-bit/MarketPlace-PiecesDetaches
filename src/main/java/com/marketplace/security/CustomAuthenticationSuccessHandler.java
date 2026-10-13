package com.marketplace.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        logger.info("Authentication successful for user: {}", authentication.getName());
        logger.info("Authorities: {}", authentication.getAuthorities());

        String redirectParam = request.getParameter("redirect");
        if (redirectParam != null && redirectParam.startsWith("/") && !redirectParam.startsWith("//")) {
            response.sendRedirect(request.getContextPath() + redirectParam);
            return;
        }

        String targetUrl = request.getContextPath() + determineTargetUrl(authentication);
        logger.info("Redirecting to: {}", targetUrl);

        response.sendRedirect(targetUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "/admin/module/securite";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BOUTIQUE")) ||
                   authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGER"))) {
            return "/magasinier/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MAGASINIER"))) {
            return "/magasinier/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MECANICIEN")) ||
                   authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_AGENT_ATELIER"))) {
            return "/mecanicien/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIVREUR"))) {
            return "/livreur/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CLIENT"))) {
            return "/client/catalogue";
        } else {
            return "/client/catalogue";
        }
    }
}

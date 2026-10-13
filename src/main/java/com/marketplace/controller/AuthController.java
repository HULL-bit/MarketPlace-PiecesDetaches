package com.marketplace.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.marketplace.web.LocaleSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final LocaleResolver localeResolver;

    public AuthController(AuthenticationManager authenticationManager,
                          LocaleResolver localeResolver) {
        this.authenticationManager = authenticationManager;
        this.localeResolver = localeResolver;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                           @RequestParam(required = false) String logout,
                           Model model,
                           HttpServletRequest request) {
        if (error != null) {
            model.addAttribute("error", "Email ou mot de passe incorrect");
        }
        if (logout != null) {
            model.addAttribute("message", "Déconnexion réussie");
        }
        LocaleSupport.applyLangSwitch(request, localeResolver, model.asMap(), request.getContextPath() + "/login");
        return "forward:/login.xhtml";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password,
                       Model model,
                       HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            HttpSession session = request.getSession();
            session.setAttribute("authentication", authentication);

            String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("ROLE_CLIENT");

            switch (role) {
                case "ROLE_ADMIN":
                    return "redirect:/admin/dashboard.xhtml";
                case "ROLE_MAGASINIER":
                case "ROLE_MANAGER":
                case "ROLE_BOUTIQUE":
                    return "redirect:/magasinier/dashboard";
                case "ROLE_MECANICIEN":
                case "ROLE_AGENT_ATELIER":
                    return "redirect:/mecanicien/dashboard";
                case "ROLE_LIVREUR":
                    return "redirect:/livreur/dashboard";
                default:
                    return "redirect:/client/catalogue";
            }
        } catch (BadCredentialsException e) {
            model.addAttribute("error", "Identifiants incorrects");
            return "forward:/login.xhtml";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur de connexion: " + e.getMessage());
            return "forward:/login.xhtml";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return "redirect:/login?logout=true";
    }
}

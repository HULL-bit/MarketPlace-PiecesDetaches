package com.marketplace.web;

import com.marketplace.entity.Personne;
import com.marketplace.service.PersonneService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Locale;

/**
 * Infos layout admin (utilisateur connecté, libellés) pour les vues JSF.
 */
@Component("adminLayoutView")
@RequestScope
public class AdminLayoutView {

    private final PersonneService personneService;
    private Personne currentAdmin;
    private boolean loaded;

    public AdminLayoutView(PersonneService personneService) {
        this.personneService = personneService;
    }

    public Personne getCurrentAdmin() {
        load();
        return currentAdmin;
    }

    public String getCurrentAdminInitials() {
        load();
        if (currentAdmin == null) {
            return "A";
        }
        String pr = currentAdmin.getPrenom() != null && !currentAdmin.getPrenom().isBlank()
                ? currentAdmin.getPrenom().substring(0, 1).toUpperCase(Locale.ROOT) : "";
        String no = currentAdmin.getNom() != null && !currentAdmin.getNom().isBlank()
                ? currentAdmin.getNom().substring(0, 1).toUpperCase(Locale.ROOT) : "";
        String out = (pr + no).trim();
        return out.isEmpty() ? "A" : out;
    }

    public String getCurrentAdminAvatarUrl() {
        load();
        if (currentAdmin == null) {
            return "https://ui-avatars.com/api/?name=Admin&background=2c3e50&color=ffffff&size=128";
        }
        String fullName = ((currentAdmin.getPrenom() != null ? currentAdmin.getPrenom() : "") + " "
                + (currentAdmin.getNom() != null ? currentAdmin.getNom() : "")).trim();
        String safe = fullName.isBlank() ? "Admin" : fullName.replace(" ", "+");
        return "https://ui-avatars.com/api/?name=" + safe + "&background=2c3e50&color=ffffff&size=128";
    }

    public String getDisplayName() {
        load();
        if (currentAdmin == null) {
            return "Administrateur";
        }
        return (currentAdmin.getPrenom() != null ? currentAdmin.getPrenom() : "") + " "
                + (currentAdmin.getNom() != null ? currentAdmin.getNom() : "");
    }

    private void load() {
        if (loaded) {
            return;
        }
        loaded = true;
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getName() != null && !"anonymousUser".equals(auth.getName())) {
            currentAdmin = personneService.findByLogin(auth.getName()).orElse(null);
        }
    }
}

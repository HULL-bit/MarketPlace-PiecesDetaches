package com.marketplace.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.entity.Boutique;
import com.marketplace.entity.Personne;
import com.marketplace.entity.Piece;
import com.marketplace.repository.PrixPieceRepository;
import com.marketplace.service.BoutiqueService;
import com.marketplace.service.PersonneService;
import com.marketplace.service.PieceService;
import com.marketplace.web.CataloguePageSupport;
import com.marketplace.web.GuestCartSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.marketplace.web.LocaleSupport;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private BoutiqueService boutiqueService;

    @Autowired
    private PieceService pieceService;

    @Autowired
    private PersonneService personneService;

    @Autowired
    private PrixPieceRepository prixPieceRepository;

    @Autowired
    private LocaleResolver localeResolver;

    @GetMapping({"/", "/index", "/pieces-auto", "/pieces-auto/", "/pieces-auto/index"})
    public String index(Model model, HttpServletRequest request, HttpSession session) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        model.addAttribute("currentDate", now.format(formatter));

        LocaleSupport.applyLangSwitch(request, localeResolver, model.asMap(), "/");

        List<Boutique> boutiques = boutiqueService.findAll();
        model.addAttribute("boutiques", boutiques);

        try {
            ObjectMapper mapper = new ObjectMapper();
            // Centre de repli (Dakar) pour les boutiques sans position GPS renseignée,
            // légèrement décalé par index pour éviter que les marqueurs ne se superposent.
            double fallbackLat = 14.6937;
            double fallbackLng = -17.4467;
            List<Map<String, Object>> boutiquesJson = new java.util.ArrayList<>();
            for (int i = 0; i < boutiques.size(); i++) {
                Boutique b = boutiques.get(i);
                Map<String, Object> map = new HashMap<>();
                map.put("id", b.getIdboutique());
                map.put("nom", b.getLibelle());
                map.put("adresse", b.getDescription() != null ? b.getDescription() : "Dakar, Sénégal");
                map.put("tel", b.getMobile1() != null ? b.getMobile1().toString() : "");
                map.put("lat", b.getLatitude() != null ? b.getLatitude() : fallbackLat + (i % 5) * 0.01);
                map.put("lng", b.getLongitude() != null ? b.getLongitude() : fallbackLng + (i % 3) * 0.01);
                boutiquesJson.add(map);
            }
            model.addAttribute("boutiquesJson", mapper.writeValueAsString(boutiquesJson));
        } catch (JsonProcessingException e) {
            model.addAttribute("boutiquesJson", "[]");
        }

        List<Piece> populairesSource = CataloguePageSupport.availableWithRelations(pieceService).stream()
                .limit(6)
                .toList();
        model.addAttribute("piecesPopulaires", CataloguePageSupport.toCatalogueItems(populairesSource, prixPieceRepository));

        model.addAttribute("cartCount", GuestCartSupport.countItems(session));

        return "forward:/index.xhtml";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request, Model model) {
        LocaleSupport.applyLangSwitch(request, localeResolver, model.asMap(), request.getContextPath() + "/register");
        return "forward:/register.xhtml";
    }

    /**
     * Inscription publique : crée une {@link Personne} et une ligne {@code RoleUtilisateur}
     * selon le profil métier (CLIENT, BOUTIQUE, AGENT_ATELIER pour « mécanicien »).
     */
    @PostMapping("/register")
    public String registerSubmit(@RequestParam String prenom,
                                 @RequestParam String nom,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam(required = false) String tel,
                                 @RequestParam(required = false) String adresse,
                                 @RequestParam(required = false) String boutiqueNom,
                                 @RequestParam(required = false) String specialite,
                                 @RequestParam String role,
                                 RedirectAttributes ra) {
        String em = email.trim().toLowerCase(Locale.ROOT);
        if (personneService.existsByEmail(em)) {
            ra.addFlashAttribute("error", "Cette adresse e-mail est déjà enregistrée.");
            return "redirect:/register";
        }

        String profileId = mapPublicRegistrationRole(role);
        String normalizedRole = role != null ? role.trim().toUpperCase(Locale.ROOT) : "CLIENT";

        if ("BOUTIQUE".equals(normalizedRole)) {
            if (isBlank(tel) || isBlank(boutiqueNom) || isBlank(adresse)) {
                ra.addFlashAttribute("error", "Compte Boutique: téléphone, nom de boutique et adresse sont obligatoires.");
                return "redirect:/register";
            }
        } else if ("MECANICIEN".equals(normalizedRole)) {
            if (isBlank(tel) || isBlank(specialite) || isBlank(adresse)) {
                ra.addFlashAttribute("error", "Compte Mécanicien: téléphone, spécialité et adresse sont obligatoires.");
                return "redirect:/register";
            }
        }

        String login = em;
        int suffix = 0;
        while (personneService.existsByLogin(login)) {
            login = em.replace("@", "_") + (suffix++);
        }

        Personne p = new Personne();
        p.setPrenom(prenom.trim());
        p.setNom(nom.trim());
        p.setEmail(em);
        p.setLogin(login);
        p.setMotpasse(password);
        p.setEtat("ACTIF");
        p.setAdresse(adresse != null && !adresse.isBlank() ? adresse.trim() : null);
        if ("BOUTIQUE".equals(normalizedRole)) {
            p.setLieunaiss(boutiqueNom != null ? boutiqueNom.trim() : null);
        } else if ("MECANICIEN".equals(normalizedRole)) {
            p.setLieunaiss(specialite != null ? specialite.trim() : null);
        }
        if (tel != null && !tel.isBlank()) {
            try {
                p.setTel(Long.parseLong(tel.replaceAll("\\D", "")));
            } catch (NumberFormatException ignored) {
                /* téléphone optionnel */
            }
        }

        try {
            personneService.createWithProfile(p, profileId);
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", "Impossible de finaliser l'inscription. Réessayez ou contactez le support.");
            return "redirect:/register";
        }

        ra.addFlashAttribute("message", "Inscription réussie. Vous pouvez vous connecter.");
        return "redirect:/login";
    }

    private static String mapPublicRegistrationRole(String role) {
        if (role == null) {
            return "CLIENT";
        }
        switch (role.toUpperCase(Locale.ROOT)) {
            case "BOUTIQUE":
                return "BOUTIQUE";
            case "MECANICIEN":
                return "AGENT_ATELIER";
            default:
                return "CLIENT";
        }
    }

    private static boolean isBlank(String v) {
        return v == null || v.isBlank();
    }
}

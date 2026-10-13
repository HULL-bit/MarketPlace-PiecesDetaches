package com.marketplace.controller;

import com.marketplace.entity.Boutique;
import com.marketplace.entity.BranchePermission;
import com.marketplace.entity.CategoriePiece;
import com.marketplace.entity.Personne;
import com.marketplace.entity.PeriodeConfig;
import com.marketplace.entity.Permission;
import com.marketplace.entity.PermissionUtilisateur;
import com.marketplace.entity.PieceModele;
import com.marketplace.entity.Profile;
import com.marketplace.entity.RoleProfile;
import com.marketplace.service.*;
import com.marketplace.web.LocaleSupport;
import com.marketplace.repository.BranchePermissionRepository;
import com.marketplace.repository.PermissionRepository;
import com.marketplace.repository.PermissionUtilisateurRepository;
import com.marketplace.repository.PeriodeConfigRepository;
import com.marketplace.repository.PieceModeleRepository;
import com.marketplace.repository.RoleProfileRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final PersonneService personneService;
    private final BoutiqueService boutiqueService;
    private final PieceService pieceService;
    private final CommandeService commandeService;
    private final ProfileService profileService;
    private final CategoriePieceService categoriePieceService;
    private final AdminDashboardService adminDashboardService;
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    private final BranchePermissionRepository branchePermissionRepository;
    private final RoleProfileRepository roleProfileRepository;
    private final PermissionUtilisateurRepository permissionUtilisateurRepository;
    private final PeriodeConfigRepository periodeConfigRepository;
    private final PieceModeleRepository pieceModeleRepository;
    private final LocaleResolver localeResolver;

    public AdminController(PersonneService personneService,
                          BoutiqueService boutiqueService,
                          PieceService pieceService,
                          CommandeService commandeService,
                          ProfileService profileService,
                          CategoriePieceService categoriePieceService,
                          AdminDashboardService adminDashboardService,
                          ClientService clientService,
                          PasswordEncoder passwordEncoder,
                          PermissionRepository permissionRepository,
                          BranchePermissionRepository branchePermissionRepository,
                          RoleProfileRepository roleProfileRepository,
                          PermissionUtilisateurRepository permissionUtilisateurRepository,
                          PeriodeConfigRepository periodeConfigRepository,
                          PieceModeleRepository pieceModeleRepository,
                          LocaleResolver localeResolver) {
        this.personneService = personneService;
        this.boutiqueService = boutiqueService;
        this.pieceService = pieceService;
        this.commandeService = commandeService;
        this.profileService = profileService;
        this.categoriePieceService = categoriePieceService;
        this.adminDashboardService = adminDashboardService;
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
        this.permissionRepository = permissionRepository;
        this.branchePermissionRepository = branchePermissionRepository;
        this.roleProfileRepository = roleProfileRepository;
        this.permissionUtilisateurRepository = permissionUtilisateurRepository;
        this.periodeConfigRepository = periodeConfigRepository;
        this.pieceModeleRepository = pieceModeleRepository;
        this.localeResolver = localeResolver;
    }

    private void adminLayout(Model model, String navKey, String title) {
        model.addAttribute("adminNav", navKey);
        model.addAttribute("adminPageTitle", title);
        model.addAttribute("currentDate", LocalDate.now().format(DATE_FMT));
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest req = attrs.getRequest();
            LocaleSupport.applyLangSwitch(req, localeResolver, model.asMap(), req.getRequestURI());
        }
        Principal principal = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (principal != null && principal.getName() != null && !"anonymousUser".equals(principal.getName())) {
            personneService.findByLogin(principal.getName()).ifPresent(admin -> {
                model.addAttribute("currentAdmin", admin);
                model.addAttribute("currentAdminInitials", initials(admin));
                model.addAttribute("currentAdminAvatarUrl", buildAvatarUrl(admin));
            });
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:/admin/module/securite";
    }

    @GetMapping("/module/securite")
    public String moduleSecurite(@RequestParam(defaultValue = "utilisateurs") String tab, Model model) {
        adminLayout(model, "module-securite", "Dashboard Sécurité");
        model.addAttribute("activeTab", tab);
        model.addAttribute("dash", adminDashboardService.buildSnapshot());
        model.addAttribute("utilisateurs", personneService.findAll());
        model.addAttribute("profiles", profileService.findAll());
        model.addAttribute("permissions", permissionRepository.findAll());
        model.addAttribute("roleProfiles", roleProfileRepository.findAll());
        model.addAttribute("branches", branchePermissionRepository.findAll());
        model.addAttribute("permissionUtilisateurs", permissionUtilisateurRepository.findAll());
        return "admin/module-securite";
    }

    @GetMapping("/module/pieces")
    public String modulePieces(@RequestParam(defaultValue = "pieces") String tab, Model model) {
        adminLayout(model, "module-pieces", "Dashboard Pièces");
        model.addAttribute("activeTab", tab);
        model.addAttribute("dash", adminDashboardService.buildSnapshot());
        model.addAttribute("pieces", pieceService.findAllWithBoutiqueAndCategorie());
        model.addAttribute("categories", categoriePieceService.findAll());
        model.addAttribute("allCategories", categoriePieceService.findAll());
        model.addAttribute("commandes", commandeService.findAll());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("boutiques", boutiqueService.findAll());
        model.addAttribute("anneesConfig", periodeConfigRepository.findByTypeOrderByOrdreAsc("ANNEE"));
        model.addAttribute("pieceModeles", pieceModeleRepository.findAll());
        return "admin/module-pieces";
    }

    @GetMapping("/module/boutique")
    public String moduleBoutique(@RequestParam(defaultValue = "boutiques") String tab, Model model) {
        adminLayout(model, "module-boutique", "Dashboard Boutique");
        model.addAttribute("activeTab", tab);
        model.addAttribute("dash", adminDashboardService.buildSnapshot());
        model.addAttribute("boutiques", boutiqueService.findAll());
        return "admin/module-boutique";
    }

    @GetMapping("/module/parametres")
    public String moduleParametres(@RequestParam(defaultValue = "initialisation") String tab, Model model, Principal principal) {
        adminLayout(model, "module-parametres", "Dashboard Paramètres");
        model.addAttribute("activeTab", tab);
        model.addAttribute("dash", adminDashboardService.buildSnapshot());
        model.addAttribute("totalCommandes", commandeService.findAll().size());
        model.addAttribute("totalClients", personneService.findAll().stream().filter(u -> "CLIENT".equals(u.getRole())).count());
        model.addAttribute("totalBoutiques", boutiqueService.findAll().size());
        model.addAttribute("totalPieces", pieceService.findAll().size());
        populateInitialisation(model);
        if (principal != null) {
            personneService.findByLogin(principal.getName()).ifPresent(u -> model.addAttribute("user", u));
        }
        return "admin/module-parametres";
    }

    private void populateInitialisation(Model model) {
        model.addAttribute("stats", java.util.Map.of(
                "annees", (int) periodeConfigRepository.countByType("ANNEE"),
                "mois", (int) periodeConfigRepository.countByType("MOIS"),
                "jours", (int) periodeConfigRepository.countByType("JOUR"),
                "boutiques", boutiqueService.findAll().size(),
                "categories", categoriePieceService.findAll().size()
        ));
        model.addAttribute("annees", periodeConfigRepository.findByTypeOrderByOrdreAsc("ANNEE"));
        model.addAttribute("mois", periodeConfigRepository.findByTypeOrderByOrdreAsc("MOIS"));
        model.addAttribute("jours", periodeConfigRepository.findByTypeOrderByOrdreAsc("JOUR"));
        if (!model.containsAttribute("boutiques")) {
            model.addAttribute("boutiques", boutiqueService.findAll());
        }
        if (!model.containsAttribute("categories")) {
            model.addAttribute("categories", categoriePieceService.findAll());
        }
    }

    @GetMapping("/utilisateurs")
    public String utilisateurs() {
        return "redirect:/admin/module/securite?tab=utilisateurs";
    }

    @PostMapping("/utilisateurs/create")
    public String createUser(@RequestParam String nom,
                             @RequestParam String prenom,
                             @RequestParam String email,
                             @RequestParam String login,
                             @RequestParam String password,
                             @RequestParam String profileId,
                             @RequestParam(required = false) String telephone,
                             @RequestParam(required = false) String telephone2,
                             @RequestParam(required = false) String sexe,
                             @RequestParam(required = false) String datenaiss,
                             @RequestParam(required = false) String lieunaiss,
                             @RequestParam(required = false) String adresse,
                             RedirectAttributes ra) {
        String em = email.trim().toLowerCase(Locale.ROOT);
        String log = login.trim();
        if (personneService.existsByEmail(em) || personneService.existsByLogin(log)) {
            ra.addFlashAttribute("error", "Email ou identifiant déjà utilisé.");
            return "redirect:/admin/module/securite?tab=utilisateurs";
        }
        Personne p = new Personne();
        p.setNom(nom.trim());
        p.setPrenom(prenom.trim());
        p.setEmail(em);
        p.setLogin(log);
        p.setMotpasse(password);
        p.setEtat("ACTIF");
        if (telephone != null && !telephone.isBlank()) {
            try {
                p.setTel(Long.parseLong(telephone.replaceAll("\\D", "")));
            } catch (NumberFormatException ignored) {
                /* ignore invalid phone */
            }
        }
        if (telephone2 != null && !telephone2.isBlank()) {
            try {
                p.setTel2(Long.parseLong(telephone2.replaceAll("\\D", "")));
            } catch (NumberFormatException ignored) {
                /* ignore invalid phone */
            }
        }
        p.setSexe(sexe != null && !sexe.isBlank() ? sexe.trim() : null);
        p.setLieunaiss(lieunaiss != null && !lieunaiss.isBlank() ? lieunaiss.trim() : null);
        p.setAdresse(adresse != null && !adresse.isBlank() ? adresse.trim() : null);
        if (datenaiss != null && !datenaiss.isBlank()) {
            try {
                p.setDatenaiss(LocalDate.parse(datenaiss));
            } catch (Exception ignored) {
                /* ignore invalid date */
            }
        }
        try {
            personneService.createWithProfile(p, profileId);
            ra.addFlashAttribute("success", "Utilisateur créé avec succès.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", "Profil ou données invalides.");
        }
        return "redirect:/admin/module/securite?tab=utilisateurs";
    }

    @PostMapping("/utilisateurs/update")
    public String updateUser(@RequestParam Integer idpersonne,
                             @RequestParam String nom,
                             @RequestParam String prenom,
                             @RequestParam String email,
                             @RequestParam String login,
                             @RequestParam String etat,
                             @RequestParam String profileId,
                             @RequestParam(required = false) String telephone,
                             @RequestParam(required = false) String telephone2,
                             @RequestParam(required = false) String sexe,
                             @RequestParam(required = false) String datenaiss,
                             @RequestParam(required = false) String lieunaiss,
                             @RequestParam(required = false) String adresse,
                             @RequestParam(required = false) String password,
                             RedirectAttributes ra) {
        String em = email.trim().toLowerCase(Locale.ROOT);
        String log = login.trim();
        Personne existing = personneService.findById(idpersonne).orElse(null);
        if (existing == null) {
            ra.addFlashAttribute("error", "Utilisateur introuvable.");
            return "redirect:/admin/module/securite?tab=utilisateurs";
        }
        if (personneService.findByEmail(em).filter(o -> !o.getIdpersonne().equals(idpersonne)).isPresent()
                || personneService.findByLogin(log).filter(o -> !o.getIdpersonne().equals(idpersonne)).isPresent()) {
            ra.addFlashAttribute("error", "Email ou identifiant déjà utilisé.");
            return "redirect:/admin/module/securite?tab=utilisateurs";
        }
        try {
            personneService.updateFromAdmin(idpersonne, nom.trim(), prenom.trim(), em, log, etat, profileId, password);
            personneService.findById(idpersonne).ifPresent(user -> {
                user.setSexe(sexe != null && !sexe.isBlank() ? sexe.trim() : null);
                user.setLieunaiss(lieunaiss != null && !lieunaiss.isBlank() ? lieunaiss.trim() : null);
                user.setAdresse(adresse != null && !adresse.isBlank() ? adresse.trim() : null);
                if (telephone != null && !telephone.isBlank()) {
                    try { user.setTel(Long.parseLong(telephone.replaceAll("\\D", ""))); } catch (NumberFormatException ignored) { }
                }
                if (telephone2 != null && !telephone2.isBlank()) {
                    try { user.setTel2(Long.parseLong(telephone2.replaceAll("\\D", ""))); } catch (NumberFormatException ignored) { }
                }
                if (datenaiss != null && !datenaiss.isBlank()) {
                    try { user.setDatenaiss(LocalDate.parse(datenaiss)); } catch (Exception ignored) { }
                }
                personneService.update(user);
            });
            ra.addFlashAttribute("success", "Utilisateur mis à jour.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", "Données invalides.");
        }
        return "redirect:/admin/module/securite?tab=utilisateurs";
    }

    @GetMapping("/utilisateurs/{id}/delete")
    public String deleteUser(@PathVariable Integer id, Principal principal, RedirectAttributes ra) {
        Personne current = personneService.findByLogin(principal.getName()).orElse(null);
        if (current != null && current.getIdpersonne().equals(id)) {
            ra.addFlashAttribute("error", "Vous ne pouvez pas supprimer votre propre compte.");
            return "redirect:/admin/module/securite?tab=utilisateurs";
        }
        try {
            personneService.deleteWithRoles(id);
            ra.addFlashAttribute("success", "Utilisateur supprimé.");
        } catch (DataIntegrityViolationException ex) {
            ra.addFlashAttribute("error", "Suppression impossible : cet utilisateur est encore référencé par des commandes ou autres données.");
        }
        return "redirect:/admin/module/securite?tab=utilisateurs";
    }

    @GetMapping("/boutiques")
    public String boutiques() {
        return "redirect:/admin/module/boutique?tab=boutiques";
    }

    @PostMapping("/boutiques/save")
    public String saveBoutique(@RequestParam(required = false) Integer idboutique,
                               @RequestParam String libelle,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) String description,
                               @RequestParam(required = false) String telephone,
                               @RequestParam(required = false) String telephone2,
                               @RequestParam(required = false) String telephone3,
                               @RequestParam(required = false) String telFixe,
                               @RequestParam(required = false) String modepaielivraison,
                               @RequestParam(required = false) String logo,
                               @RequestParam(required = false) String banniere,
                               @RequestParam(required = false) Double latitude,
                               @RequestParam(required = false) Double longitude,
                               @RequestParam(defaultValue = "ACTIF") String etat,
                               RedirectAttributes ra) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Long tel = null;
        if (telephone != null && !telephone.isBlank()) {
            try {
                tel = Long.parseLong(telephone.replaceAll("\\D", ""));
            } catch (NumberFormatException ignored) { /* keep null */ }
        }
        final Long mobileFinal = tel;
        final Long mobile2 = parsePhone(telephone2);
        final Long mobile3 = parsePhone(telephone3);
        final Long telF = parsePhone(telFixe);
        if (idboutique != null && idboutique > 0) {
            boutiqueService.findById(idboutique).ifPresent(b -> {
                b.setLibelle(libelle.trim());
                b.setEmail(email != null ? email.trim() : null);
                b.setDescription(description);
                b.setMobile1(mobileFinal);
                b.setMobile2(mobile2);
                b.setMobile3(mobile3);
                b.setTel(telF);
                b.setModepaielivraison(modepaielivraison != null ? modepaielivraison.trim() : null);
                b.setLogo(logo != null ? logo.trim() : null);
                b.setBanniere(banniere != null ? banniere.trim() : null);
                b.setLatitude(latitude);
                b.setLongitude(longitude);
                b.setEtat(etat != null && !etat.isBlank() ? etat.trim() : "ACTIF");
                b.setDatemaj(today);
                boutiqueService.update(b);
            });
            ra.addFlashAttribute("success", "Boutique mise à jour.");
        } else {
            Boutique b = new Boutique();
            b.setLibelle(libelle.trim());
            b.setEmail(email != null ? email.trim() : null);
            b.setDescription(description);
            b.setMobile1(mobileFinal);
            b.setMobile2(mobile2);
            b.setMobile3(mobile3);
            b.setTel(telF);
            b.setModepaielivraison(modepaielivraison != null ? modepaielivraison.trim() : null);
            b.setLogo(logo != null ? logo.trim() : null);
            b.setBanniere(banniere != null ? banniere.trim() : null);
            b.setLatitude(latitude);
            b.setLongitude(longitude);
            b.setDate(today);
            b.setEtat(etat != null && !etat.isBlank() ? etat.trim() : "ACTIF");
            boutiqueService.save(b);
            ra.addFlashAttribute("success", "Boutique créée.");
        }
        return "redirect:/admin/module/boutique?tab=boutiques";
    }

    @GetMapping("/boutiques/{id}/delete")
    public String deleteBoutique(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            boutiqueService.delete(id);
            ra.addFlashAttribute("success", "Boutique supprimée.");
        } catch (DataIntegrityViolationException ex) {
            ra.addFlashAttribute("error", "Suppression impossible : des pièces ou autres données sont encore liées à cette boutique.");
        }
        return "redirect:/admin/module/boutique?tab=boutiques";
    }

    @GetMapping("/pieces")
    public String pieces() {
        return "redirect:/admin/module/pieces?tab=pieces";
    }

    @PostMapping("/pieces/save")
    public String savePiece(@RequestParam(required = false) Integer idpiece,
                            @RequestParam String libelle,
                            @RequestParam(required = false) String ref,
                            @RequestParam(required = false) String description,
                            @RequestParam(required = false) String marque,
                            @RequestParam(required = false) String modele,
                            @RequestParam(required = false) String annee,
                            @RequestParam(required = false) String serie,
                            @RequestParam(defaultValue = "DISPONIBLE") String etat,
                            @RequestParam(required = false) Integer idboutique,
                            @RequestParam(required = false) Integer idcatpiece,
                            @RequestParam(required = false) Integer idpiecemodele,
                            @RequestParam(required = false) String image,
                            @RequestParam(required = false) Integer achat,
                            @RequestParam(required = false) Integer vente,
                            @RequestParam(required = false) Integer tva,
                            @RequestParam(required = false) Integer reduction,
                            @RequestParam(required = false) Integer code,
                            @RequestParam(required = false) Integer nombre,
                            @RequestParam(required = false) String couleur,
                            @RequestParam(required = false) String nouvelleCategorie,
                            RedirectAttributes ra) {
        try {
            pieceService.saveOrUpdateFromAdmin(idpiece, libelle, ref, description, etat, idboutique, idcatpiece,
                    marque, modele, annee, serie, idpiecemodele, image, achat, vente, tva, reduction, code, nombre,
                    couleur, nouvelleCategorie);
            ra.addFlashAttribute("success", idpiece != null && idpiece > 0 ? "Pièce mise à jour." : "Pièce créée.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/module/pieces?tab=pieces";
    }

    @GetMapping("/pieces/{id}/delete")
    public String deletePiece(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            pieceService.delete(id);
            ra.addFlashAttribute("success", "Pièce supprimée.");
        } catch (DataIntegrityViolationException ex) {
            ra.addFlashAttribute("error", "Suppression impossible : la pièce est encore référencée (commandes, paniers, etc.).");
        }
        return "redirect:/admin/module/pieces?tab=pieces";
    }

    @GetMapping("/profil")
    public String profil() {
        return "redirect:/admin/module/parametres?tab=profil";
    }

    @PostMapping("/profil/update")
    public String updateProfil(Principal principal,
                                 @RequestParam String nom,
                                 @RequestParam String prenom,
                                 @RequestParam String email,
                                 @RequestParam(required = false) String telephone,
                                 @RequestParam(required = false) String telephone2,
                                 @RequestParam(required = false) String adresse,
                                 @RequestParam(required = false) String lieunaiss,
                                 @RequestParam(required = false) String sexe,
                                 @RequestParam(required = false) String datenaiss,
                                 @RequestParam(required = false) String motpasse,
                                 RedirectAttributes ra) {
        Personne user = personneService.findByLogin(principal.getName()).orElseThrow();
        user.setNom(nom.trim());
        user.setPrenom(prenom.trim());
        user.setEmail(email.trim().toLowerCase(Locale.ROOT));
        user.setAdresse(adresse != null ? adresse.trim() : null);
        user.setLieunaiss(lieunaiss != null ? lieunaiss.trim() : null);
        user.setSexe(sexe != null ? sexe.trim() : null);
        if (datenaiss != null && !datenaiss.isBlank()) {
            try {
                user.setDatenaiss(LocalDate.parse(datenaiss));
            } catch (Exception ignored) {
                // ignore invalid date input
            }
        }
        if (telephone != null && !telephone.isBlank()) {
            try {
                user.setTel(Long.parseLong(telephone.replaceAll("\\D", "")));
            } catch (NumberFormatException ignored) { /* skip */ }
        }
        if (telephone2 != null && !telephone2.isBlank()) {
            try {
                user.setTel2(Long.parseLong(telephone2.replaceAll("\\D", "")));
            } catch (NumberFormatException ignored) { /* skip */ }
        }
        if (motpasse != null && !motpasse.isBlank()) {
            user.setMotpasse(passwordEncoder.encode(motpasse));
        }
        user.setDatemaj(LocalDateTime.now());
        personneService.update(user);
        ra.addFlashAttribute("success", "Profil mis à jour.");
        return "redirect:/admin/module/parametres?tab=profil";
    }

    private static String initials(Personne p) {
        String pr = p.getPrenom() != null && !p.getPrenom().isBlank() ? p.getPrenom().substring(0, 1).toUpperCase(Locale.ROOT) : "";
        String no = p.getNom() != null && !p.getNom().isBlank() ? p.getNom().substring(0, 1).toUpperCase(Locale.ROOT) : "";
        String out = (pr + no).trim();
        return out.isEmpty() ? "A" : out;
    }

    private static String buildAvatarUrl(Personne p) {
        String fullName = ((p.getPrenom() != null ? p.getPrenom() : "") + " " + (p.getNom() != null ? p.getNom() : "")).trim();
        String safe = fullName.isBlank() ? "Admin" : fullName.replace(" ", "+");
        return "https://ui-avatars.com/api/?name=" + safe + "&background=2c3e50&color=ffffff&size=128";
    }

    private static Long parsePhone(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Long.parseLong(value.replaceAll("\\D", ""));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    @GetMapping("/initialisation")
    public String initialisation() {
        return "redirect:/admin/module/parametres?tab=initialisation";
    }

    @PostMapping("/initialisation/periode")
    public String addPeriode(@RequestParam String type,
                             @RequestParam String nom,
                             @RequestParam String code,
                             @RequestParam(required = false) Integer ordre,
                             RedirectAttributes ra) {
        String t = type != null ? type.trim().toUpperCase(Locale.ROOT) : "";
        if (!Arrays.asList("ANNEE", "MOIS", "JOUR").contains(t)) {
            ra.addFlashAttribute("error", "Type invalide (ANNEE/MOIS/JOUR).");
            return "redirect:/admin/module/parametres?tab=initialisation";
        }
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        PeriodeConfig pc = new PeriodeConfig();
        pc.setType(t);
        pc.setNom(nom.trim());
        pc.setCode(code.trim());
        pc.setEtat("ACTIF");
        pc.setOrdre(ordre != null ? ordre : 0);
        pc.setDate(today);
        periodeConfigRepository.save(pc);
        ra.addFlashAttribute("success", "Période enregistrée (" + t + ").");
        return "redirect:/admin/module/parametres?tab=initialisation";
    }

    @GetMapping("/initialisation/periode/{id}/delete")
    public String deletePeriode(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            periodeConfigRepository.deleteById(id);
            ra.addFlashAttribute("success", "Période supprimée.");
        } catch (DataIntegrityViolationException ex) {
            ra.addFlashAttribute("error", "Suppression impossible : période référencée.");
        }
        return "redirect:/admin/module/parametres?tab=initialisation";
    }

    @PostMapping("/initialisation/boutique")
    public String addBoutique(@RequestParam String nom,
                             @RequestParam String proprietaire,
                             @RequestParam String ville,
                             @RequestParam String telephone,
                             @RequestParam(required = false) String adresse,
                             RedirectAttributes ra) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Long tel = null;
        try {
            tel = Long.parseLong(telephone.replaceAll("\\D", ""));
        } catch (NumberFormatException e) {
            ra.addFlashAttribute("error", "Téléphone invalide.");
            return "redirect:/admin/module/parametres?tab=initialisation";
        }
        String desc = (adresse != null ? adresse : "") + "\nPropriétaire: " + proprietaire + "\nVille: " + ville;
        Boutique b = new Boutique();
        b.setLibelle(nom.trim());
        b.setDescription(desc.trim());
        b.setMobile1(tel);
        b.setDate(today);
        b.setEtat("ACTIF");
        boutiqueService.save(b);
        ra.addFlashAttribute("success", "Boutique ajoutée.");
        return "redirect:/admin/module/parametres?tab=initialisation";
    }

    @PostMapping("/initialisation/categorie")
    public String addCategorie(@RequestParam String nom,
                              @RequestParam(required = false) String description,
                              RedirectAttributes ra) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String refBase = nom.trim().toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9]", "");
        String ref = refBase.isEmpty() ? "CAT" : refBase.substring(0, Math.min(12, refBase.length()));
        CategoriePiece cat = new CategoriePiece();
        cat.setLibelle(nom.trim());
        cat.setRef(ref);
        cat.setDate(today);
        cat.setNombre(0);
        categoriePieceService.save(cat);
        ra.addFlashAttribute("success", "Catégorie ajoutée.");
        return "redirect:/admin/module/parametres?tab=initialisation";
    }

    @PostMapping("/initialisation/seed")
    public String seedData(RedirectAttributes ra) {
        if (boutiqueService.findAll().isEmpty()) {
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Boutique b = new Boutique();
            b.setLibelle("Boutique démo AutoPieces");
            b.setDescription("Propriétaire: Démo\nVille: Dakar");
            b.setMobile1(770000000L);
            b.setDate(today);
            b.setEtat("ACTIF");
            boutiqueService.save(b);
            ra.addFlashAttribute("success", "Données de démonstration : une boutique exemple a été créée.");
        } else {
            ra.addFlashAttribute("success", "Les boutiques existent déjà — aucune donnée supplémentaire insérée.");
        }
        return "redirect:/admin/module/parametres?tab=initialisation";
    }

    @PostMapping("/initialisation/reset")
    public String resetDatabase(RedirectAttributes ra) {
        ra.addFlashAttribute("error",
                "Réinitialisation complète désactivée dans l'application (sécurité). Utilisez une migration SQL ou videz la base manuellement si nécessaire.");
        return "redirect:/admin/module/parametres?tab=initialisation";
    }

    @GetMapping("/carte")
    public String carte() {
        return "redirect:/admin/module/boutique?tab=carte";
    }

    @GetMapping("/commandes")
    public String commandes() {
        return "redirect:/admin/module/pieces?tab=commandes";
    }

    @PostMapping("/commandes/create")
    public String createCommande(@RequestParam Integer idclient,
                               @RequestParam(required = false) Integer code,
                               @RequestParam(required = false) Integer nombrearticle,
                               @RequestParam(defaultValue = "EN_ATTENTE") String etat,
                               @RequestParam(required = false) String date,
                               @RequestParam(required = false) String annee,
                               @RequestParam(required = false) String modelivraison,
                               RedirectAttributes ra) {
        try {
            var created = commandeService.createMinimalForAdmin(idclient, nombrearticle, etat, date, modelivraison);
            if (created != null) {
                created.setCode(code);
                created.setAnnee(annee != null && !annee.isBlank() ? annee.trim() : null);
                commandeService.update(created);
            }
            ra.addFlashAttribute("success", "Commande créée.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/module/pieces?tab=commandes";
    }

    @PostMapping("/commandes/update")
    public String updateCommande(@RequestParam Integer idcommande,
                                 @RequestParam String etat,
                                 @RequestParam(required = false) Integer code,
                                 @RequestParam(required = false) Integer nombrearticle,
                                 @RequestParam(required = false) String date,
                                 @RequestParam(required = false) String annee,
                                 @RequestParam(required = false) String modelivraison,
                                 RedirectAttributes ra) {
        try {
            commandeService.updateFromAdmin(idcommande, etat, nombrearticle, modelivraison, date);
            commandeService.findById(idcommande).ifPresent(cmd -> {
                cmd.setCode(code);
                cmd.setAnnee(annee != null && !annee.isBlank() ? annee.trim() : null);
                commandeService.update(cmd);
            });
            ra.addFlashAttribute("success", "Commande mise à jour.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/module/pieces?tab=commandes";
    }

    @GetMapping("/commandes/{id}/delete")
    public String deleteCommande(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            commandeService.delete(id);
            ra.addFlashAttribute("success", "Commande supprimée.");
        } catch (DataIntegrityViolationException ex) {
            ra.addFlashAttribute("error", "Suppression impossible : des lignes ou factures sont encore liées à cette commande.");
        }
        return "redirect:/admin/module/pieces?tab=commandes";
    }

    @GetMapping("/categories")
    public String categories() {
        return "redirect:/admin/module/pieces?tab=categories";
    }

    @PostMapping("/categories/save")
    public String saveCategorie(@RequestParam(required = false) Integer idcatpiece,
                                @RequestParam String libelle,
                                @RequestParam(required = false) String ref,
                                @RequestParam(required = false) Integer code,
                                @RequestParam(required = false) Integer parentId,
                                RedirectAttributes ra) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (idcatpiece != null && idcatpiece > 0) {
            Optional<CategoriePiece> existing = categoriePieceService.findById(idcatpiece);
            if (existing.isEmpty()) {
                ra.addFlashAttribute("error", "Catégorie introuvable.");
                return "redirect:/admin/module/pieces?tab=categories";
            }
            CategoriePiece cat = existing.get();
            cat.setLibelle(libelle.trim());
            if (ref != null && !ref.isBlank()) {
                cat.setRef(ref.trim());
            }
            cat.setCode(code);
            if (parentId != null && parentId > 0) {
                categoriePieceService.findById(parentId).ifPresent(cat::setParent);
            } else {
                cat.setParent(null);
            }
            cat.setDatemaj(today);
            categoriePieceService.update(cat);
            ra.addFlashAttribute("success", "Catégorie mise à jour.");
        } else {
            String refBase = libelle.trim().toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9]", "");
            String refFinal = ref != null && !ref.isBlank() ? ref.trim()
                    : (refBase.isEmpty() ? "CAT" : refBase.substring(0, Math.min(12, refBase.length())));
            CategoriePiece cat = new CategoriePiece();
            cat.setLibelle(libelle.trim());
            cat.setRef(refFinal);
            cat.setCode(code);
            if (parentId != null && parentId > 0) {
                categoriePieceService.findById(parentId).ifPresent(cat::setParent);
            }
            cat.setDate(today);
            cat.setNombre(0);
            categoriePieceService.save(cat);
            ra.addFlashAttribute("success", "Catégorie créée.");
        }
        return "redirect:/admin/module/pieces?tab=categories";
    }

    @GetMapping("/categories/{id}/delete")
    public String deleteCategorie(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            categoriePieceService.delete(id);
            ra.addFlashAttribute("success", "Catégorie supprimée.");
        } catch (DataIntegrityViolationException ex) {
            ra.addFlashAttribute("error", "Suppression impossible : des pièces utilisent encore cette catégorie.");
        }
        return "redirect:/admin/module/pieces?tab=categories";
    }

    @PostMapping("/pieces-modeles/save")
    public String savePieceModele(@RequestParam(required = false) Integer idpiecemodele,
                                  @RequestParam String libelle,
                                  @RequestParam(required = false) String marque,
                                  @RequestParam(required = false) String modele,
                                  @RequestParam(required = false) String annee,
                                  RedirectAttributes ra) {
        if (idpiecemodele != null && idpiecemodele > 0) {
            PieceModele existing = pieceModeleRepository.findById(idpiecemodele).orElse(null);
            if (existing == null) {
                ra.addFlashAttribute("error", "Modèle introuvable.");
                return "redirect:/admin/module/pieces?tab=modeles";
            }
            existing.setLibelle(libelle != null && !libelle.isBlank() ? libelle.trim() : null);
            existing.setMarque(marque != null && !marque.isBlank() ? marque.trim() : null);
            existing.setModele(modele != null && !modele.isBlank() ? modele.trim() : null);
            existing.setAnnee(annee != null && !annee.isBlank() ? annee.trim() : null);
            pieceModeleRepository.save(existing);
            ra.addFlashAttribute("success", "Modèle mis à jour.");
        } else {
            PieceModele pm = new PieceModele();
            pm.setLibelle(libelle != null && !libelle.isBlank() ? libelle.trim() : null);
            pm.setMarque(marque != null && !marque.isBlank() ? marque.trim() : null);
            pm.setModele(modele != null && !modele.isBlank() ? modele.trim() : null);
            pm.setAnnee(annee != null && !annee.isBlank() ? annee.trim() : null);
            pieceModeleRepository.save(pm);
            ra.addFlashAttribute("success", "Modèle créé.");
        }
        return "redirect:/admin/module/pieces?tab=modeles";
    }

    @GetMapping("/pieces-modeles/{id}/delete")
    public String deletePieceModele(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            pieceModeleRepository.deleteById(id);
            ra.addFlashAttribute("success", "Modèle supprimé.");
        } catch (DataIntegrityViolationException ex) {
            ra.addFlashAttribute("error", "Suppression impossible : des pièces sont encore associées à ce modèle.");
        }
        return "redirect:/admin/module/pieces?tab=modeles";
    }

    @GetMapping("/rapports")
    public String rapports() {
        return "redirect:/admin/module/parametres?tab=rapports";
    }

    @GetMapping("/parametres")
    public String parametres() {
        return "redirect:/admin/module/parametres?tab=apparence";
    }

    @PostMapping("/parametres")
    public String parametresPost(RedirectAttributes ra) {
        ra.addFlashAttribute("success", "Préférences enregistrées (démonstration).");
        return "redirect:/admin/module/parametres?tab=apparence";
    }

    @GetMapping("/permissions")
    public String permissions() {
        return "redirect:/admin/module/securite?tab=permission-branche";
    }

    @PostMapping("/permissions/save")
    public String savePermission(@RequestParam(required = false) Integer idpermission,
                                 @RequestParam String libelle,
                                 @RequestParam(required = false) String ref,
                                 @RequestParam(defaultValue = "ACTIF") String etat,
                                 @RequestParam(required = false) Integer idbranche,
                                 RedirectAttributes ra) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Permission p = (idpermission != null && idpermission > 0)
                ? permissionRepository.findById(idpermission).orElse(new Permission())
                : new Permission();
        if (idpermission != null && idpermission > 0 && p.getIdpermission() == null) {
            ra.addFlashAttribute("error", "Permission introuvable.");
            return "redirect:/admin/module/securite?tab=permission-branche";
        }
        p.setLibelle(libelle.trim());
        p.setRef(ref != null && !ref.isBlank() ? ref.trim() : null);
        p.setEtat(etat != null && !etat.isBlank() ? etat.trim() : "ACTIF");

        BranchePermission selectedBranch = null;
        if (idbranche != null && idbranche > 0) {
            selectedBranch = branchePermissionRepository.findById(idbranche).orElse(null);
        }
        p.setBranche(selectedBranch);

        // Auto-compute permission code based on branch code
        if (selectedBranch != null && selectedBranch.getCode() != null) {
            int branchCode = selectedBranch.getCode();
            Integer maxPermCode = permissionRepository.findMaxCodeByBranchCode(branchCode);
            int nextPermCode;
            if (maxPermCode != null && maxPermCode > branchCode) {
                nextPermCode = maxPermCode + 1;
            } else {
                nextPermCode = branchCode + 1;
            }
            p.setCode(nextPermCode);
        } else {
            p.setCode(null);
        }

        if (p.getIdpermission() != null) {
            p.setDatemaj(today);
        } else {
            p.setDate(today);
        }
        permissionRepository.save(p);
        ra.addFlashAttribute("success", (p.getIdpermission() != null && idpermission != null && idpermission > 0)
                ? "Permission mise à jour."
                : "Permission créée (code: " + p.getCode() + ").");
        return "redirect:/admin/module/securite?tab=permission-branche";
    }

    @GetMapping("/permissions/{id}/delete")
    public String deletePermission(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            permissionRepository.deleteById(id);
            ra.addFlashAttribute("success", "Permission supprimée.");
        } catch (DataIntegrityViolationException ex) {
            ra.addFlashAttribute("error", "Suppression impossible : cette permission est encore liée à un rôle.");
        }
        return "redirect:/admin/module/securite?tab=permission-branche";
    }

    @GetMapping("/roles")
    public String roles() {
        return "redirect:/admin/module/securite?tab=profils";
    }

    @PostMapping("/profils/save")
    public String saveProfilMetier(@RequestParam String idprofile,
                                   @RequestParam String libelle,
                                   @RequestParam(defaultValue = "ACTIF") String etat,
                                   RedirectAttributes ra) {
        return saveRoleInternal(idprofile, libelle, etat, ra, "redirect:/admin/module/securite?tab=profils");
    }

    private String saveRoleInternal(String idprofile, String libelle, String etat, RedirectAttributes ra, String redirect) {
        String id = idprofile.trim().toUpperCase(Locale.ROOT);
        if (id.isBlank()) {
            ra.addFlashAttribute("error", "Identifiant du profil invalide.");
            return redirect;
        }
        Profile existing = profileService.findById(id);
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (existing != null) {
            existing.setLibelle(libelle.trim());
            existing.setEtat(etat != null && !etat.isBlank() ? etat.trim() : "ACTIF");
            existing.setDatemaj(today);
            profileService.save(existing);
            ra.addFlashAttribute("success", "Profil mis à jour.");
        } else {
            Profile p = new Profile();
            p.setIdprofile(id);
            p.setLibelle(libelle.trim());
            p.setEtat(etat != null && !etat.isBlank() ? etat.trim() : "ACTIF");
            p.setDate(today);
            profileService.save(p);
            ra.addFlashAttribute("success", "Profil créé.");
        }
        return redirect;
    }

    @GetMapping("/profils/{id}/delete")
    public String deleteProfilMetier(@PathVariable String id, RedirectAttributes ra) {
        try {
            roleProfileRepository.deleteByProfile_Idprofile(id);
            profileService.delete(id);
            ra.addFlashAttribute("success", "Profil supprimé.");
        } catch (DataIntegrityViolationException ex) {
            ra.addFlashAttribute("error", "Suppression impossible : ce profil est encore utilisé.");
        }
        return "redirect:/admin/module/securite?tab=profils";
    }

    @PostMapping("/profils/permissions/save")
    public String saveProfilPermissions(@RequestParam String idprofile,
                                        @RequestParam(required = false) List<Integer> permissionIds,
                                        RedirectAttributes ra) {
        String profileId = idprofile.trim().toUpperCase(Locale.ROOT);
        Profile prof = profileService.findById(profileId);
        if (prof == null) {
            ra.addFlashAttribute("error", "Profil introuvable.");
            return "redirect:/admin/module/securite?tab=profils";
        }
        roleProfileRepository.deleteByProfile_Idprofile(profileId);
        if (permissionIds != null && !permissionIds.isEmpty()) {
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            for (Integer pid : permissionIds) {
                if (pid == null) continue;
                permissionRepository.findById(pid).ifPresent(perm -> {
                    RoleProfile rp = new RoleProfile();
                    rp.setProfile(prof);
                    rp.setPermission(perm);
                    rp.setEtat("ACTIF");
                    rp.setDate(today);
                    roleProfileRepository.save(rp);
                });
            }
        }
        ra.addFlashAttribute("success", "Permissions du profil mises à jour. Tous les utilisateurs de ce profil en héritent.");
        return "redirect:/admin/module/securite?tab=profils";
    }

    @GetMapping("/profils")
    public String profilsMetier() {
        return "redirect:/admin/module/securite?tab=profils";
    }

    @GetMapping("/branches")
    public String branches() {
        return "redirect:/admin/module/securite?tab=permission-branche";
    }

    @PostMapping("/branches/save")
    public String saveBranche(@RequestParam(required = false) Integer idbranche,
                              @RequestParam String libelle,
                              @RequestParam(required = false) String ref,
                              @RequestParam(required = false) Integer niveau,
                              @RequestParam(defaultValue = "ACTIF") String etat,
                              RedirectAttributes ra) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        BranchePermission b = (idbranche != null && idbranche > 0)
                ? branchePermissionRepository.findById(idbranche).orElse(new BranchePermission())
                : new BranchePermission();
        if (idbranche != null && idbranche > 0 && b.getIdbranche() == null) {
            ra.addFlashAttribute("error", "Branche introuvable.");
            return "redirect:/admin/module/securite?tab=permission-branche";
        }
        b.setLibelle(libelle.trim());
        b.setRef(ref != null && !ref.isBlank() ? ref.trim() : null);
        boolean isNew = (b.getIdbranche() == null);
        if (isNew) {
            // Always auto-compute code for new branches: multiples of 1000
            Integer maxCode = branchePermissionRepository.findMaxCode();
            int base = (maxCode != null ? maxCode : 0);
            int nextCode = ((base / 1000) + 1) * 1000;
            b.setCode(nextCode);
        } else {
            // For existing branches: fix code if it's not a valid multiple of 1000
            Integer currentCode = b.getCode();
            if (currentCode == null || currentCode <= 0 || currentCode % 1000 != 0) {
                Integer maxCode = branchePermissionRepository.findMaxCode();
                int base = (maxCode != null ? maxCode : 0);
                // Ensure new code is above existing max
                int nextCode = ((base / 1000) + 1) * 1000;
                b.setCode(nextCode);
            }
        }
        b.setNiveau(niveau != null ? niveau : 0);
        b.setEtat(etat != null && !etat.isBlank() ? etat.trim() : "ACTIF");
        if (b.getIdbranche() != null) {
            b.setDatemaj(today);
        } else {
            b.setDate(today);
        }
        branchePermissionRepository.save(b);
        boolean wasUpdate = idbranche != null && idbranche > 0;
        ra.addFlashAttribute("success", wasUpdate ? "Branche mise à jour." : "Branche créée (code: " + b.getCode() + ").");
        return "redirect:/admin/module/securite?tab=permission-branche";
    }

    @GetMapping("/branches/{id}/delete")
    public String deleteBranche(@PathVariable Integer id, RedirectAttributes ra) {
        List<Permission> attached = permissionRepository.findByBrancheIdbranche(id);
        for (Permission p : attached) {
            p.setBranche(null);
            permissionRepository.save(p);
        }
        try {
            branchePermissionRepository.deleteById(id);
            ra.addFlashAttribute("success", "Branche supprimée.");
        } catch (DataIntegrityViolationException ex) {
            ra.addFlashAttribute("error", "Suppression impossible.");
        }
        return "redirect:/admin/module/securite?tab=permission-branche";
    }

    @PostMapping("/utilisateurs/permissions/save")
    public String saveUserPermissions(@RequestParam Integer idpersonne,
                                      @RequestParam(required = false) List<Integer> permissionIds,
                                      RedirectAttributes ra) {
        Personne user = personneService.findById(idpersonne).orElse(null);
        if (user == null) {
            ra.addFlashAttribute("error", "Utilisateur introuvable.");
            return "redirect:/admin/module/securite?tab=utilisateurs";
        }
        // Remove all existing direct permissions for this user
        permissionUtilisateurRepository.deleteByPersonne_Idpersonne(idpersonne);
        // Add the new direct permissions
        if (permissionIds != null && !permissionIds.isEmpty()) {
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            for (Integer pid : permissionIds) {
                if (pid == null) continue;
                permissionRepository.findById(pid).ifPresent(perm -> {
                    PermissionUtilisateur pu = new PermissionUtilisateur();
                    pu.setPersonne(user);
                    pu.setPermission(perm);
                    pu.setEtat("ACTIF");
                    pu.setDate(today);
                    permissionUtilisateurRepository.save(pu);
                });
            }
        }
        ra.addFlashAttribute("success", "Permissions de l'utilisateur mises à jour.");
        return "redirect:/admin/module/securite?tab=utilisateurs";
    }
}

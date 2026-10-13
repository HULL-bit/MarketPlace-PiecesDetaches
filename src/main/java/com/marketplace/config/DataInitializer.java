package com.marketplace.config;

import com.marketplace.entity.*;
import com.marketplace.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DataInitializer.class);

    private final ProfileRepository profileRepository;
    private final PermissionRepository permissionRepository;
    private final PersonneRepository personneRepository;
    private final CategoriePieceRepository categoriePieceRepository;
    private final RoleUtilisateurRepository roleUtilisateurRepository;
    private final BranchePermissionRepository branchePermissionRepository;
    private final PeriodeConfigRepository periodeConfigRepository;
    private final PieceModeleRepository pieceModeleRepository;
    private final BoutiqueRepository boutiqueRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(ProfileRepository profileRepository, PermissionRepository permissionRepository,
            PersonneRepository personneRepository,
            CategoriePieceRepository categoriePieceRepository, RoleUtilisateurRepository roleUtilisateurRepository,
            BranchePermissionRepository branchePermissionRepository,
            PeriodeConfigRepository periodeConfigRepository,
            PieceModeleRepository pieceModeleRepository,
            BoutiqueRepository boutiqueRepository,
            PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.permissionRepository = permissionRepository;
        this.personneRepository = personneRepository;
        this.categoriePieceRepository = categoriePieceRepository;
        this.roleUtilisateurRepository = roleUtilisateurRepository;
        this.branchePermissionRepository = branchePermissionRepository;
        this.periodeConfigRepository = periodeConfigRepository;
        this.pieceModeleRepository = pieceModeleRepository;
        this.boutiqueRepository = boutiqueRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Vérification des données initiales...");

        initProfiles();
        initBranches();
        initPermissions();
        migrateBranchAndPermissionCodes();
        initAdminUser();
        initTestAccounts();
        initDefaultCategories();
        initPeriodes();
        initDefaultPieceModeles();
        initDefaultBoutique();

        log.info("Initialisation des données terminée.");
    }

    private void migrateBranchAndPermissionCodes() {
        List<BranchePermission> branches = branchePermissionRepository.findAll();
        if (branches.isEmpty()) return;

        boolean migrated = false;
        int nextMultiple = 1000;

        // Sort branches by existing code to preserve relative ordering
        branches.sort((a, b) -> {
            int ca = a.getCode() != null ? a.getCode() : 0;
            int cb = b.getCode() != null ? b.getCode() : 0;
            return Integer.compare(ca, cb);
        });

        for (BranchePermission b : branches) {
            Integer code = b.getCode();
            boolean needsFix = (code == null || code <= 0 || code % 1000 != 0);
            if (needsFix) {
                int oldCode = (code != null) ? code : 0;
                b.setCode(nextMultiple);
                branchePermissionRepository.save(b);
                log.info("Branche '{}' (id={}): code {} → {}", b.getLibelle(), b.getIdbranche(), oldCode, nextMultiple);

                // Recompute permission codes for this branch
                List<Permission> perms = permissionRepository.findByBrancheIdbranche(b.getIdbranche());
                int permCode = nextMultiple + 1;
                for (Permission p : perms) {
                    if (p.getCode() == null || p.getCode() < nextMultiple || p.getCode() >= nextMultiple + 1000) {
                        p.setCode(permCode);
                        permissionRepository.save(p);
                        log.info("  Permission '{}': code → {}", p.getLibelle(), permCode);
                    }
                    permCode++;
                }
                migrated = true;
            }
            nextMultiple += 1000;
        }

        if (migrated) {
            log.info("Migration des codes branches/permissions terminée.");
        }
    }

    private void initProfiles() {
        if (profileRepository.count() == 0) {
            log.info("Création des profils par défaut...");
            
            createProfileIfNotExists("ADMIN", "Administrateur", "ACTIF");
            createProfileIfNotExists("CLIENT", "Client", "ACTIF");
            createProfileIfNotExists("BOUTIQUE", "Responsable Boutique", "ACTIF");
            createProfileIfNotExists("LIVREUR", "Livreur", "ACTIF");
            createProfileIfNotExists("AGENT_ATELIER", "Agent Atelier", "ACTIF");
            createProfileIfNotExists("MANAGER", "Manager", "ACTIF");
        }
    }

    private void createProfileIfNotExists(String id, String libelle, String etat) {
        if (!profileRepository.existsById(id)) {
            Profile profile = new Profile();
            profile.setIdprofile(id);
            profile.setLibelle(libelle);
            profile.setEtat(etat);
            profileRepository.save(profile);
            log.info("Profil créé: {}", libelle);
        }
    }

    private void initBranches() {
        if (branchePermissionRepository.count() == 0) {
            log.info("Création des branches par défaut...");
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            BranchePermission adminBranch = new BranchePermission();
            adminBranch.setLibelle("Administration");
            adminBranch.setCode(1000);
            adminBranch.setRef("ADMIN");
            adminBranch.setNiveau(0);
            adminBranch.setEtat("ACTIF");
            adminBranch.setDate(today);
            branchePermissionRepository.save(adminBranch);

            BranchePermission clientBranch = new BranchePermission();
            clientBranch.setLibelle("Client");
            clientBranch.setCode(3000);
            clientBranch.setRef("CLIENT");
            clientBranch.setNiveau(0);
            clientBranch.setEtat("ACTIF");
            clientBranch.setDate(today);
            branchePermissionRepository.save(clientBranch);

            log.info("Branches créées: Administration (1000), Client (3000)");
        }
    }

    private void initPermissions() {
        if (permissionRepository.count() == 0) {
            log.info("Création des permissions par défaut...");
            
            createPermissionIfNotExists(10001, "Gestion utilisateurs", "USER_MGT", 1000);
            createPermissionIfNotExists(10002, "Gestion boutiques", "BTQ_MGT", 1000);
            createPermissionIfNotExists(10003, "Gestion pièces", "PIECE_MGT", 1000);
            createPermissionIfNotExists(10004, "Gestion commandes", "CMD_MGT", 1000);
            createPermissionIfNotExists(30001, "Voir catalogue", "VIEW_CAT", 3000);
            createPermissionIfNotExists(30002, "Passer commande", "PASS_CMD", 3000);
        }
    }

    private void createPermissionIfNotExists(Integer id, String libelle, String ref, Integer branchCode) {
        if (!permissionRepository.existsById(id)) {
            Permission perm = new Permission();
            perm.setIdpermission(id);
            perm.setLibelle(libelle);
            perm.setRef(ref);
            perm.setCode(id);
            perm.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            perm.setEtat("ACTIF");
            // Link to branch by code
            branchePermissionRepository.findAll().stream()
                    .filter(b -> b.getCode() != null && b.getCode().equals(branchCode))
                    .findFirst()
                    .ifPresent(perm::setBranche);
            permissionRepository.save(perm);
            log.info("Permission créée: {} (code: {}, branche: {})", libelle, id, branchCode);
        }
    }

    private void initAdminUser() {
        // Chercher l'admin existant par login ou email
        Optional<Personne> existingAdmin = personneRepository.findByLogin("admin")
                .or(() -> personneRepository.findByEmail("admin@marketplace.com"));
        
        if (existingAdmin.isPresent()) {
            Personne admin = existingAdmin.get();
            log.info("Mise à jour de l'administrateur existant (id={})...", admin.getIdpersonne());
            admin.setPrenom("Admin");
            admin.setNom("System");
            admin.setRole("ADMIN");
            admin.setEtat("ACTIF");
            admin.setEmail("admin@marketplace.com");
            String encodedPassword = passwordEncoder.encode("admin123");
            admin.setMotpasse(encodedPassword);
            personneRepository.save(admin);
            log.info("Admin mis à jour avec nouveau mot de passe");
            
            // Vérifier/créer le RoleUtilisateur
            ensureAdminRole(admin);
            return;
        }
        
        log.info("Création de l'administrateur par défaut...");
        Personne admin = new Personne();
        admin.setPrenom("Admin");
        admin.setNom("System");
        admin.setEmail("admin@marketplace.com");
        admin.setTel(77889900L);
        admin.setRole("ADMIN");
        admin.setEtat("ACTIF");
        admin.setLogin("admin");
        String encodedPassword = passwordEncoder.encode("admin123");
        admin.setMotpasse(encodedPassword);
        admin.setDateCreation(LocalDateTime.now());
        // IMPORTANT: Ne pas setter datenaiss (type date en DB incompatible avec String)

        personneRepository.save(admin);
        log.info("Admin sauvegardé avec mot de passe encodé: {}", encodedPassword.substring(0, 20) + "...");

        // Créer le RoleUtilisateur pour l'admin
        Profile adminProfile = profileRepository.findById("ADMIN").orElse(null);
        if (adminProfile != null) {
            RoleUtilisateur roleUtilisateur = new RoleUtilisateur();
            roleUtilisateur.setPersonne(admin);
            roleUtilisateur.setProfile(adminProfile);
            roleUtilisateur.setEtat("ACTIF");
            roleUtilisateur.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            roleUtilisateurRepository.save(roleUtilisateur);
            log.info("Role ADMIN associé à l'administrateur");
        } else {
            log.error("Profile ADMIN non trouvé !");
        }

        log.info("Administrateur créé: admin / admin123");
    }

    private void ensureAdminRole(Personne admin) {
        // Vérifier si le rôle ADMIN existe déjà
        List<RoleUtilisateur> existingRoles = roleUtilisateurRepository.findByPersonneIdpersonne(admin.getIdpersonne());
        boolean hasAdminRole = existingRoles.stream()
                .anyMatch(r -> "ADMIN".equals(r.getProfile().getIdprofile()));
        
        if (!hasAdminRole) {
            Profile adminProfile = profileRepository.findById("ADMIN").orElse(null);
            if (adminProfile != null) {
                RoleUtilisateur roleUtilisateur = new RoleUtilisateur();
                roleUtilisateur.setPersonne(admin);
                roleUtilisateur.setProfile(adminProfile);
                roleUtilisateur.setEtat("ACTIF");
                roleUtilisateur.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                roleUtilisateurRepository.save(roleUtilisateur);
                log.info("Role ADMIN créé pour l'administrateur");
            } else {
                log.error("Profile ADMIN non trouvé !");
            }
        } else {
            log.info("Role ADMIN déjà existant pour l'administrateur");
        }
    }

    private void initTestAccounts() {
        createTestUserIfNotExists("client1", "client1@marketplace.com", "client123", "CLIENT", "Client", "Test", 77001001L);
        createTestUserIfNotExists("boutique1", "boutique1@marketplace.com", "boutique123", "BOUTIQUE", "Boutique", "Test", 77002002L);
        createTestUserIfNotExists("livreur1", "livreur1@marketplace.com", "livreur123", "LIVREUR", "Livreur", "Test", 77003003L);
        createTestUserIfNotExists("mecanicien1", "mecanicien1@marketplace.com", "mecanicien123", "AGENT_ATELIER", "Mécanicien", "Test", 77004004L);
    }

    private void createTestUserIfNotExists(String login, String email, String password, String role, String prenom, String nom, Long tel) {
        if (personneRepository.findByLogin(login).isPresent()) {
            log.info("Compte test déjà existant: {}", login);
            return;
        }
        log.info("Création du compte test: {}", login);
        Personne p = new Personne();
        p.setPrenom(prenom);
        p.setNom(nom);
        p.setEmail(email);
        p.setTel(tel);
        p.setRole(role);
        p.setEtat("ACTIF");
        p.setLogin(login);
        p.setMotpasse(passwordEncoder.encode(password));
        p.setDateCreation(java.time.LocalDateTime.now());
        personneRepository.save(p);

        Profile profile = profileRepository.findById(role).orElse(null);
        if (profile != null) {
            RoleUtilisateur ru = new RoleUtilisateur();
            ru.setPersonne(p);
            ru.setProfile(profile);
            ru.setEtat("ACTIF");
            ru.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            roleUtilisateurRepository.save(ru);
        }
        log.info("Compte test créé: {} / {} (role: {})", login, password, role);
    }

    private void initDefaultCategories() {
        if (categoriePieceRepository.count() == 0) {
            log.info("Création des catégories par défaut...");
            
            createCategoryIfNotExists(1, "Moteur", "MOT", null);
            createCategoryIfNotExists(2, "Transmission", "TRANS", null);
            createCategoryIfNotExists(3, "Freinage", "FREIN", null);
            createCategoryIfNotExists(4, "Suspension", "SUSP", null);
            createCategoryIfNotExists(5, "Carrosserie", "CARRO", null);
            createCategoryIfNotExists(6, "Électricité", "ELEC", null);
            createCategoryIfNotExists(7, "Échappement", "ECHAP", null);
            createCategoryIfNotExists(8, "Refroidissement", "REFROID", null);
        }
    }

    private void createCategoryIfNotExists(Integer id, String libelle, String ref, Integer parentId) {
        if (!categoriePieceRepository.existsById(id)) {
            CategoriePiece cat = new CategoriePiece();
            cat.setIdcatpiece(id);
            cat.setLibelle(libelle);
            cat.setRef(ref);
            cat.setCode(id);
            cat.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            cat.setNombre(0);
            
            if (parentId != null) {
                categoriePieceRepository.findById(parentId).ifPresent(cat::setParent);
            }
            
            categoriePieceRepository.save(cat);
            log.info("Catégorie créée: {}", libelle);
        }
    }

    private void initPeriodes() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (periodeConfigRepository.countByType("JOUR") == 0) {
            log.info("Création des 7 jours de la semaine...");
            String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
            for (int i = 0; i < jours.length; i++) {
                createPeriodeIfNotExists("JOUR", jours[i], String.valueOf(i + 1), i + 1, today);
            }
        }

        if (periodeConfigRepository.countByType("MOIS") == 0) {
            log.info("Création des 12 mois...");
            String[] mois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                    "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
            for (int i = 0; i < mois.length; i++) {
                createPeriodeIfNotExists("MOIS", mois[i], String.format("%02d", i + 1), i + 1, today);
            }
        }

        if (periodeConfigRepository.countByType("ANNEE") == 0) {
            log.info("Création des années (modèles véhicules)...");
            int currentYear = LocalDate.now().getYear();
            int firstYear = currentYear + 1;
            int lastYear = currentYear - 19; // ~20 années, la plus récente en tête
            int ordre = 0;
            for (int year = firstYear; year >= lastYear; year--) {
                createPeriodeIfNotExists("ANNEE", String.valueOf(year), String.valueOf(year), ordre, today);
                ordre++;
            }
        }
    }

    private void createPeriodeIfNotExists(String type, String nom, String code, int ordre, String today) {
        boolean exists = periodeConfigRepository.findByTypeOrderByOrdreAsc(type).stream()
                .anyMatch(p -> code.equals(p.getCode()));
        if (exists) {
            return;
        }
        PeriodeConfig pc = new PeriodeConfig();
        pc.setType(type);
        pc.setNom(nom);
        pc.setCode(code);
        pc.setEtat("ACTIF");
        pc.setOrdre(ordre);
        pc.setDate(today);
        periodeConfigRepository.save(pc);
    }

    private void initDefaultPieceModeles() {
        if (pieceModeleRepository.count() > 0) {
            return;
        }
        log.info("Création des marques/modèles véhicules par défaut...");
        int recentYear = LocalDate.now().getYear();
        String[][] modeles = {
                {"Toyota", "Corolla"},
                {"Toyota", "Hilux"},
                {"Renault", "Clio"},
                {"Renault", "Duster"},
                {"Peugeot", "208"},
                {"Peugeot", "3008"},
                {"Volkswagen", "Golf"},
                {"Hyundai", "Tucson"},
                {"Nissan", "Qashqai"},
                {"Kia", "Sportage"},
                {"Ford", "Focus"},
                {"Mercedes-Benz", "Classe C"},
        };
        for (String[] m : modeles) {
            createPieceModeleIfNotExists(m[0], m[1], String.valueOf(recentYear));
        }
    }

    private void createPieceModeleIfNotExists(String marque, String modele, String annee) {
        boolean exists = pieceModeleRepository
                .findFirstByMarqueIgnoreCaseAndModeleIgnoreCaseAndAnneeIgnoreCase(marque, modele, annee)
                .isPresent();
        if (exists) {
            return;
        }
        PieceModele pm = new PieceModele();
        pm.setMarque(marque);
        pm.setModele(modele);
        pm.setAnnee(annee);
        pm.setLibelle(marque + " " + modele + " " + annee);
        pieceModeleRepository.save(pm);
    }

    private void initDefaultBoutique() {
        if (boutiqueRepository.count() > 0) {
            return;
        }
        log.info("Création de la boutique par défaut...");
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Boutique b = new Boutique();
        b.setLibelle("Boutique démo AutoPieces");
        b.setDescription("Propriétaire: Démo\nVille: Dakar");
        b.setMobile1(770000000L);
        b.setDate(today);
        b.setEtat("ACTIF");
        boutiqueRepository.save(b);
    }
}

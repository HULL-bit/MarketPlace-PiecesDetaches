package com.marketplace.service.impl;

import com.marketplace.entity.Personne;
import com.marketplace.entity.Profile;
import com.marketplace.entity.RoleUtilisateur;
import com.marketplace.repository.PersonneRepository;
import com.marketplace.repository.ProfileRepository;
import com.marketplace.repository.RoleUtilisateurRepository;
import com.marketplace.service.PersonneService;
import com.marketplace.util.SecurityLogger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonneServiceImpl implements PersonneService {
    
    private final PersonneRepository personneRepository;
    private final ProfileRepository profileRepository;
    private final RoleUtilisateurRepository roleUtilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityLogger securityLogger;

    public PersonneServiceImpl(PersonneRepository personneRepository, ProfileRepository profileRepository,
            RoleUtilisateurRepository roleUtilisateurRepository, PasswordEncoder passwordEncoder,
            SecurityLogger securityLogger) {
        this.personneRepository = personneRepository;
        this.profileRepository = profileRepository;
        this.roleUtilisateurRepository = roleUtilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.securityLogger = securityLogger;
    }

    
    @Override
    public Personne save(Personne personne) {
        personne.setMotpasse(passwordEncoder.encode(personne.getMotpasse()));
        Personne saved = personneRepository.save(personne);
        securityLogger.logAction("CREATE", "personne", saved.getIdpersonne().toString());
        return saved;
    }
    
    @Override
    public Personne update(Personne personne) {
        Personne updated = personneRepository.save(personne);
        securityLogger.logAction("UPDATE", "personne", updated.getIdpersonne().toString());
        return updated;
    }
    
    @Override
    public void delete(Integer id) {
        deleteWithRoles(id);
    }

    @Override
    public void deleteWithRoles(Integer idpersonne) {
        roleUtilisateurRepository.deleteByPersonne_Idpersonne(idpersonne);
        if (personneRepository.existsById(idpersonne)) {
            personneRepository.deleteById(idpersonne);
            securityLogger.logAction("DELETE", "personne", idpersonne.toString());
        }
    }

    @Override
    public Personne createWithProfile(Personne personne, String profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Profil inconnu: " + profileId));
        personne.setMotpasse(passwordEncoder.encode(personne.getMotpasse()));
        personne.setRole(profileId);
        Personne saved = personneRepository.save(personne);
        RoleUtilisateur ru = new RoleUtilisateur();
        ru.setPersonne(saved);
        ru.setProfile(profile);
        ru.setEtat("ACTIF");
        ru.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        roleUtilisateurRepository.save(ru);
        securityLogger.logAction("CREATE", "personne", saved.getIdpersonne().toString());
        return saved;
    }

    @Override
    public Personne updateFromAdmin(Integer idpersonne, String nom, String prenom, String email, String login,
                                    String etat, String profileId, String plainPassword) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Profil inconnu: " + profileId));
        Personne p = personneRepository.findById(idpersonne)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable: " + idpersonne));
        p.setNom(nom);
        p.setPrenom(prenom);
        p.setEmail(email);
        p.setLogin(login);
        p.setEtat(etat);
        p.setRole(profileId);
        if (plainPassword != null && !plainPassword.isBlank()) {
            p.setMotpasse(passwordEncoder.encode(plainPassword));
        }
        Personne saved = personneRepository.save(p);
        roleUtilisateurRepository.deleteByPersonne_Idpersonne(idpersonne);
        RoleUtilisateur ru = new RoleUtilisateur();
        ru.setPersonne(saved);
        ru.setProfile(profile);
        ru.setEtat("ACTIF");
        ru.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        roleUtilisateurRepository.save(ru);
        securityLogger.logAction("UPDATE", "personne", saved.getIdpersonne().toString());
        return saved;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Personne> findById(Integer id) {
        return personneRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Personne> findAll() {
        return personneRepository.findAll();
    }
    
    // Méthodes du DAO Générique
    @Transactional(readOnly = true)
    public List<Personne> findAllActive() {
        return personneRepository.findAllActive();
    }
    
    @Transactional(readOnly = true)
    public long countActive() {
        return personneRepository.countActive();
    }
    
    @Transactional(readOnly = true)
    public List<Personne> search(String searchTerm) {
        return personneRepository.search(searchTerm);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Personne> findByEmail(String email) {
        return personneRepository.findByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Personne> findByLogin(String login) {
        return personneRepository.findByLogin(login);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return personneRepository.existsByEmail(email);
    }
    
    @Override
    public boolean existsByLogin(String login) {
        return personneRepository.existsByLogin(login);
    }
    
    @Override
    public void activate(Integer id) {
        personneRepository.findById(id).ifPresent(p -> {
            p.setEtat("ACTIF");
            personneRepository.save(p);
            securityLogger.logAction("ACTIVATE", "personne", id.toString());
        });
    }
    
    @Override
    public void deactivate(Integer id) {
        personneRepository.findById(id).ifPresent(p -> {
            p.setEtat("INACTIF");
            personneRepository.save(p);
            securityLogger.logAction("DEACTIVATE", "personne", id.toString());
        });
    }
}

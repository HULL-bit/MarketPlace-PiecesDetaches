package com.marketplace.service;

import com.marketplace.entity.Personne;
import java.util.List;
import java.util.Optional;

public interface PersonneService {
    Personne save(Personne personne);
    Personne update(Personne personne);
    void delete(Integer id);
    Optional<Personne> findById(Integer id);
    List<Personne> findAll();
    Optional<Personne> findByEmail(String email);
    Optional<Personne> findByLogin(String login);
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    void activate(Integer id);
    void deactivate(Integer id);

    /** Crée une personne avec mot de passe en clair, encode et associe le profil Spring ({@code RoleUtilisateur}). */
    Personne createWithProfile(Personne personne, String profileId);

    /** Met à jour les champs principaux ; si {@code plainPassword} non vide, réencode le mot de passe. Rôle métier = profil. */
    Personne updateFromAdmin(Integer idpersonne, String nom, String prenom, String email, String login,
                             String etat, String profileId, String plainPassword);

    /** Supprime les liaisons profil puis la personne. */
    void deleteWithRoles(Integer idpersonne);
}

package com.marketplace.service;

import com.marketplace.entity.Commande;
import java.util.List;
import java.util.Optional;

public interface CommandeService {
    Commande save(Commande commande);
    Commande update(Commande commande);
    void delete(Integer id);
    Optional<Commande> findById(Integer id);
    List<Commande> findAll();
    List<Commande> findByClient(Integer idclient);
    List<Commande> findByEtat(String etat);
    List<Commande> findByLivreur(Integer idlivreur);
    Long countByEtat(String etat);
    Integer calculateTotalRevenue();
    void updateEtat(Integer id, String etat);
    void generateQRCode(Integer id);

    /** Commande simplifiée rattachée à un client (sans panier). */
    Commande createMinimalForAdmin(Integer idclient, Integer nombrearticle, String etat, String date, String modelivraison);

    /** Mise à jour des champs éditables depuis l’admin. */
    void updateFromAdmin(Integer idcommande, String etat, Integer nombrearticle, String modelivraison, String date);
}

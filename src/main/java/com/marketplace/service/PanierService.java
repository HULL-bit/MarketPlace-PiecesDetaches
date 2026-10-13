package com.marketplace.service;

import com.marketplace.entity.Panier;
import java.util.List;
import java.util.Optional;

public interface PanierService {
    Panier save(Panier panier);
    Panier update(Panier panier);
    void delete(Integer id);
    Optional<Panier> findById(Integer id);
    List<Panier> findAll();
    List<Panier> findByClient(Integer idclient);
    Optional<Panier> findActivePanierByClient(Integer idclient);
    Panier createOrGetPanier(Integer idclient);
    void addLigne(Integer idpanier, Integer idpiece, Integer quantite);
    void removeLigne(Integer idligne);
    void updateQuantite(Integer idligne, Integer quantite);
    void clearPanier(Integer idpanier);
    double calculateTotal(Integer idpanier);
}

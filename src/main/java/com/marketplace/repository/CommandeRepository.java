package com.marketplace.repository;

import com.marketplace.entity.Commande;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends GenericRepository<Commande, Integer> {
    
    List<Commande> findByClientIdclient(Integer idclient);
    
    List<Commande> findByEtat(String etat);
    
    @Query("SELECT c FROM Commande c WHERE c.livraison.livreur.idlivreur = :idlivreur")
    List<Commande> findByLivreur(@Param("idlivreur") Integer idlivreur);
    
    @Query("SELECT COUNT(c) FROM Commande c WHERE c.etat = :etat")
    Long countByEtat(@Param("etat") String etat);
    
    @Query("SELECT SUM(l.prix * l.quantite) FROM Commande c JOIN c.lignes l WHERE c.etat = 'LIVREE'")
    Integer calculateTotalRevenue();
}

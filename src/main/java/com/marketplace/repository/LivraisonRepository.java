package com.marketplace.repository;

import com.marketplace.entity.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Integer> {
    
    Optional<Livraison> findByCommandeIdcommande(Integer idcommande);
    
    List<Livraison> findByLivreurIdlivreur(Integer idlivreur);
    
    List<Livraison> findByEtat(String etat);
    
    @Query("SELECT l FROM Livraison l WHERE l.livreur.idlivreur = :idlivreur AND l.etat = 'EN_COURS'")
    List<Livraison> findActiveDeliveriesByLivreur(@Param("idlivreur") Integer idlivreur);
}

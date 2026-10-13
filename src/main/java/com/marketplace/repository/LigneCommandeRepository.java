package com.marketplace.repository;

import com.marketplace.entity.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Integer> {
    
    List<LigneCommande> findByCommandeIdcommande(Integer idcommande);
    
    List<LigneCommande> findByPieceIdpiece(Integer idpiece);
}

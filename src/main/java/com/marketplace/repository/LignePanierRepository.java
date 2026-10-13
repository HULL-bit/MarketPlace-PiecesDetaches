package com.marketplace.repository;

import com.marketplace.entity.LignePanier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LignePanierRepository extends JpaRepository<LignePanier, Integer> {
    
    List<LignePanier> findByPanierIdpanier(Integer idpanier);
    
    Optional<LignePanier> findByPanierIdpanierAndPieceIdpiece(Integer idpanier, Integer idpiece);
    
    void deleteByPanierIdpanier(Integer idpanier);
}

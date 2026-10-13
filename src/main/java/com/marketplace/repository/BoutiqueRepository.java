package com.marketplace.repository;

import com.marketplace.entity.Boutique;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoutiqueRepository extends GenericRepository<Boutique, Integer> {
    
    List<Boutique> findByEtat(String etat);
    
    List<Boutique> findByLibelleContainingIgnoreCase(String libelle);
    
    Optional<Boutique> findByQrcode(String qrcode);
    
    @Query("SELECT b FROM Boutique b WHERE b.etat = 'ACTIF'")
    List<Boutique> findAllActive();
    
    @Query("SELECT COUNT(b) FROM Boutique b WHERE b.etat = 'ACTIF'")
    Long countActiveBoutiques();
}

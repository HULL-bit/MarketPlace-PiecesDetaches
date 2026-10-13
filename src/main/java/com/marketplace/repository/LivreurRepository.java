package com.marketplace.repository;

import com.marketplace.entity.Livreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivreurRepository extends JpaRepository<Livreur, Integer> {
    
    Optional<Livreur> findByPersonneIdpersonne(Integer idpersonne);
    
    List<Livreur> findByEtat(String etat);
    
    List<Livreur> findByLivreurBoutiquesBoutiqueIdboutique(Integer idboutique);
}

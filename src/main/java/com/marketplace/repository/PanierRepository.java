package com.marketplace.repository;

import com.marketplace.entity.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Integer> {
    
    List<Panier> findByClientIdclient(Integer idclient);
    
    Optional<Panier> findByClientIdclientAndEtat(Integer idclient, String etat);
    
    List<Panier> findByEtat(String etat);
}

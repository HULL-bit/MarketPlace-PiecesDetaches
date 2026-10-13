package com.marketplace.repository;

import com.marketplace.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    
    List<Promotion> findByBoutiqueIdboutique(Integer idboutique);
    
    List<Promotion> findByEtat(String etat);
    
    List<Promotion> findByCodepromo(String codepromo);
}

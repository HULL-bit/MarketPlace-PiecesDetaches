package com.marketplace.repository;

import com.marketplace.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    
    List<Stock> findByBoutiqueIdboutique(Integer idboutique);
    
    Optional<Stock> findByQrcode(String qrcode);
    
    List<Stock> findByEtat(String etat);
}

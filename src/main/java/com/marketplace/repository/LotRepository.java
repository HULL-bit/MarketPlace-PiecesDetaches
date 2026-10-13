package com.marketplace.repository;

import com.marketplace.entity.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Integer> {
    
    List<Lot> findByStockIdstock(Integer idstock);
    
    List<Lot> findByPieceIdpiece(Integer idpiece);
    
    List<Lot> findByEtat(String etat);
}

package com.marketplace.repository;

import com.marketplace.entity.AvisPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvisPieceRepository extends JpaRepository<AvisPiece, Integer> {
    
    List<AvisPiece> findByPieceIdpiece(Integer idpiece);
    
    List<AvisPiece> findByClientIdclient(Integer idclient);
    
    List<AvisPiece> findByEtat(String etat);
}

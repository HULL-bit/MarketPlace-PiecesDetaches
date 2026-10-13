package com.marketplace.repository;

import com.marketplace.entity.PrixPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrixPieceRepository extends JpaRepository<PrixPiece, Integer> {
    
    List<PrixPiece> findByPieceIdpiece(Integer idpiece);
    
    Optional<PrixPiece> findTopByPieceIdpieceOrderByDateDesc(Integer idpiece);
}

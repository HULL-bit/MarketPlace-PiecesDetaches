package com.marketplace.repository;

import com.marketplace.entity.DetailPiece;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailPieceRepository extends GenericRepository<DetailPiece, Integer> {
    Optional<DetailPiece> findFirstByPieceIdpieceAndCleIgnoreCase(Integer idpiece, String cle);
}

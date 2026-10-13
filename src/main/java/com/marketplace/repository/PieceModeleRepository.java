package com.marketplace.repository;

import com.marketplace.entity.PieceModele;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PieceModeleRepository extends GenericRepository<PieceModele, Integer> {
    Optional<PieceModele> findFirstByMarqueIgnoreCaseAndModeleIgnoreCaseAndAnneeIgnoreCase(
            String marque, String modele, String annee);
}

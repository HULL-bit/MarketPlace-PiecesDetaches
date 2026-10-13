package com.marketplace.repository;

import com.marketplace.entity.Piece;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PieceRepository extends GenericRepository<Piece, Integer> {
    
    List<Piece> findByBoutiqueIdboutique(Integer idboutique);
    
    List<Piece> findByCategoriePieceIdcatpiece(Integer idcatpiece);
    
    List<Piece> findByEtat(String etat);
    
    List<Piece> findByLibelleContainingIgnoreCase(String libelle);
    
    Optional<Piece> findByQrcode(String qrcode);
    
    @Query("SELECT p FROM Piece p WHERE p.etat = 'DISPONIBLE' AND p.boutique.etat = 'ACTIF'")
    List<Piece> findAllAvailable();
    
    @Query("SELECT p FROM Piece p WHERE " +
           "(:libelle IS NULL OR LOWER(p.libelle) LIKE LOWER(CONCAT('%', :libelle, '%'))) AND " +
           "(:categorieId IS NULL OR p.categoriePiece.idcatpiece = :categorieId) AND " +
           "(:boutiqueId IS NULL OR p.boutique.idboutique = :boutiqueId)")
    List<Piece> searchPieces(@Param("libelle") String libelle, 
                             @Param("categorieId") Integer categorieId, 
                             @Param("boutiqueId") Integer boutiqueId);
    
    @Query("SELECT COUNT(p) FROM Piece p WHERE p.etat = 'DISPONIBLE'")
    Long countAvailablePieces();

    @Query("SELECT DISTINCT p FROM Piece p LEFT JOIN FETCH p.boutique LEFT JOIN FETCH p.categoriePiece " +
           "LEFT JOIN FETCH p.pieceModele LEFT JOIN FETCH p.prix")
    List<Piece> findAllWithBoutiqueAndCategorie();
}

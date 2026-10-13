package com.marketplace.repository;

import com.marketplace.entity.CategoriePiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriePieceRepository extends JpaRepository<CategoriePiece, Integer> {

    List<CategoriePiece> findByParentIsNull();

    List<CategoriePiece> findByParentIdcatpiece(Integer parentId);

    List<CategoriePiece> findByLibelleContainingIgnoreCase(String libelle);

    boolean existsByLibelle(String libelle);

    Optional<CategoriePiece> findFirstByLibelleIgnoreCase(String libelle);
}

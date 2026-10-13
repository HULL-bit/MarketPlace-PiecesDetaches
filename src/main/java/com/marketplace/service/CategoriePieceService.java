package com.marketplace.service;

import com.marketplace.entity.CategoriePiece;
import java.util.List;
import java.util.Optional;

public interface CategoriePieceService {
    CategoriePiece save(CategoriePiece categorie);
    CategoriePiece update(CategoriePiece categorie);
    void delete(Integer id);
    Optional<CategoriePiece> findById(Integer id);
    List<CategoriePiece> findAll();
    List<CategoriePiece> findRootCategories();
    List<CategoriePiece> findByParent(Integer parentId);
    List<CategoriePiece> findByLibelleContaining(String libelle);
    boolean existsByLibelle(String libelle);
}

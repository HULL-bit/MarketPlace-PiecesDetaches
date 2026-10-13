package com.marketplace.service.impl;

import com.marketplace.entity.CategoriePiece;
import com.marketplace.repository.CategoriePieceRepository;
import com.marketplace.service.CategoriePieceService;
import com.marketplace.util.SecurityLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriePieceServiceImpl implements CategoriePieceService {
    
    private final CategoriePieceRepository categorieRepository;
    private final SecurityLogger securityLogger;

    public CategoriePieceServiceImpl(CategoriePieceRepository categorieRepository, SecurityLogger securityLogger) {
        this.categorieRepository = categorieRepository;
        this.securityLogger = securityLogger;
    }

    
    @Override
    public CategoriePiece save(CategoriePiece categorie) {
        CategoriePiece saved = categorieRepository.save(categorie);
        securityLogger.logAction("CREATE", "categorie_piece", saved.getIdcatpiece().toString());
        return saved;
    }
    
    @Override
    public CategoriePiece update(CategoriePiece categorie) {
        CategoriePiece updated = categorieRepository.save(categorie);
        securityLogger.logAction("UPDATE", "categorie_piece", updated.getIdcatpiece().toString());
        return updated;
    }
    
    @Override
    public void delete(Integer id) {
        categorieRepository.deleteById(id);
        securityLogger.logAction("DELETE", "categorie_piece", id.toString());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriePiece> findById(Integer id) {
        return categorieRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoriePiece> findAll() {
        return categorieRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoriePiece> findRootCategories() {
        return categorieRepository.findByParentIsNull();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoriePiece> findByParent(Integer parentId) {
        return categorieRepository.findByParentIdcatpiece(parentId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CategoriePiece> findByLibelleContaining(String libelle) {
        return categorieRepository.findByLibelleContainingIgnoreCase(libelle);
    }
    
    @Override
    public boolean existsByLibelle(String libelle) {
        return categorieRepository.existsByLibelle(libelle);
    }
}

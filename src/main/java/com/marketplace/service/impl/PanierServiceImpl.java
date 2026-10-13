package com.marketplace.service.impl;

import com.marketplace.entity.*;
import com.marketplace.repository.*;
import com.marketplace.service.PanierService;
import com.marketplace.util.SecurityLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PanierServiceImpl implements PanierService {
    
    private final PanierRepository panierRepository; 
    private final LignePanierRepository lignePanierRepository;
    private final PieceRepository pieceRepository;
    private final PrixPieceRepository prixPieceRepository;
    private final SecurityLogger securityLogger;

    public PanierServiceImpl(PanierRepository panierRepository, LignePanierRepository lignePanierRepository, PieceRepository pieceRepository, PrixPieceRepository prixPieceRepository, SecurityLogger securityLogger) {
        this.panierRepository = panierRepository;
        this.lignePanierRepository = lignePanierRepository;
        this.pieceRepository = pieceRepository;
        this.prixPieceRepository = prixPieceRepository;
        this.securityLogger = securityLogger;
    }

    
    @Override
    public Panier save(Panier panier) {
        return panierRepository.save(panier);
    }
    
    @Override
    public Panier update(Panier panier) {
        return panierRepository.save(panier);
    }
    
    @Override
    public void delete(Integer id) {
        panierRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Panier> findById(Integer id) {
        return panierRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Panier> findAll() {
        return panierRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Panier> findByClient(Integer idclient) {
        return panierRepository.findByClientIdclient(idclient);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Panier> findActivePanierByClient(Integer idclient) {
        return panierRepository.findByClientIdclientAndEtat(idclient, "EN_COURS");
    }
    
    @Override
    public Panier createOrGetPanier(Integer idclient) {
        return findActivePanierByClient(idclient)
            .orElseGet(() -> {
                Client client = new Client();
                client.setIdclient(idclient);
                Panier newPanier = new Panier();
                newPanier.setClient(client);
                newPanier.setEtat("EN_COURS");
                securityLogger.logAction("CREATE", "panier", "new");
                return panierRepository.save(newPanier);
            });
    }
    
    @Override
    public void addLigne(Integer idpanier, Integer idpiece, Integer quantite) {
        Optional<LignePanier> existing = lignePanierRepository.findByPanierIdpanierAndPieceIdpiece(idpanier, idpiece);
        if (existing.isPresent()) {
            LignePanier ligne = existing.get();
            ligne.setQuantite(ligne.getQuantite() + quantite);
            lignePanierRepository.save(ligne);
        } else {
            Panier panier = panierRepository.findById(idpanier).orElseThrow();
            Piece piece = pieceRepository.findById(idpiece).orElseThrow();
            LignePanier ligne = new LignePanier();
ligne.setPanier(panier);
ligne.setPiece(piece);
ligne.setQuantite(quantite);
            lignePanierRepository.save(ligne);
        }
        updatePanierTotals(idpanier);
        securityLogger.logAction("ADD", "ligne_panier", idpiece.toString());
    }
    
    @Override
    public void removeLigne(Integer idligne) {
        LignePanier ligne = lignePanierRepository.findById(idligne).orElseThrow();
        Integer idpanier = ligne.getPanier().getIdpanier();
        lignePanierRepository.delete(ligne);
        updatePanierTotals(idpanier);
        securityLogger.logAction("DELETE", "ligne_panier", idligne.toString());
    }
    
    @Override
    public void updateQuantite(Integer idligne, Integer quantite) {
        LignePanier ligne = lignePanierRepository.findById(idligne).orElseThrow();
        ligne.setQuantite(quantite);
        lignePanierRepository.save(ligne);
        updatePanierTotals(ligne.getPanier().getIdpanier());
    }
    
    @Override
    public void clearPanier(Integer idpanier) {
        List<LignePanier> lignes = lignePanierRepository.findByPanierIdpanier(idpanier);
        lignePanierRepository.deleteAll(lignes);
        updatePanierTotals(idpanier);
    }
    
    @Override
    @Transactional(readOnly = true)
    public double calculateTotal(Integer idpanier) {
        List<LignePanier> lignes = lignePanierRepository.findByPanierIdpanier(idpanier);
        return lignes.stream()
            .mapToDouble(l -> {
                PrixPiece prix = prixPieceRepository.findTopByPieceIdpieceOrderByDateDesc(l.getPiece().getIdpiece()).orElse(null);
                return prix != null ? prix.getVente() * l.getQuantite() : 0;
            })
            .sum();
    }
    
    private void updatePanierTotals(Integer idpanier) {
        Panier panier = panierRepository.findById(idpanier).orElseThrow();
        List<LignePanier> lignes = lignePanierRepository.findByPanierIdpanier(idpanier);
        panier.setNombrearticles(lignes.size());
        panier.setPrix((int)calculateTotal(idpanier));
        panierRepository.save(panier);
    }
}

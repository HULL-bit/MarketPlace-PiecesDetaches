package com.marketplace.service.impl;

import com.marketplace.entity.Boutique;
import com.marketplace.repository.BoutiqueRepository;
import com.marketplace.service.BoutiqueService;
import com.marketplace.util.QRCodeGenerator;
import com.marketplace.util.SecurityLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BoutiqueServiceImpl implements BoutiqueService {
    
    private final BoutiqueRepository boutiqueRepository;
    private final QRCodeGenerator qrCodeGenerator;
    private final SecurityLogger securityLogger;

    public BoutiqueServiceImpl(BoutiqueRepository boutiqueRepository, QRCodeGenerator qrCodeGenerator, SecurityLogger securityLogger) {
        this.boutiqueRepository = boutiqueRepository;
        this.qrCodeGenerator = qrCodeGenerator;
        this.securityLogger = securityLogger;
    }

    
    @Override
    public Boutique save(Boutique boutique) {
        Boutique saved = boutiqueRepository.save(boutique);
        generateQRCode(saved.getIdboutique());
        securityLogger.logAction("CREATE", "boutique", saved.getIdboutique().toString());
        return saved;
    }
    
    @Override
    public Boutique update(Boutique boutique) {
        Boutique updated = boutiqueRepository.save(boutique);
        securityLogger.logAction("UPDATE", "boutique", updated.getIdboutique().toString());
        return updated;
    }
    
    @Override
    public void delete(Integer id) {
        boutiqueRepository.deleteById(id);
        securityLogger.logAction("DELETE", "boutique", id.toString());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Boutique> findById(Integer id) {
        return boutiqueRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Boutique> findAll() {
        return boutiqueRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Boutique> findByEtat(String etat) {
        return boutiqueRepository.findByEtat(etat);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Boutique> findByLibelleContaining(String libelle) {
        return boutiqueRepository.findByLibelleContainingIgnoreCase(libelle);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Boutique> findByQrcode(String qrcode) {
        return boutiqueRepository.findByQrcode(qrcode);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long countActiveBoutiques() {
        // Utilisation de la méthode générique du DAO
        return boutiqueRepository.countActive();
    }
    
    // Méthodes du DAO Générique
    @Transactional(readOnly = true)
    public List<Boutique> findAllActive() {
        return boutiqueRepository.findAllActive();
    }
    
    @Transactional(readOnly = true)
    public List<Boutique> search(String searchTerm) {
        return boutiqueRepository.search(searchTerm);
    }
    
    @Override
    public void generateQRCode(Integer id) {
        boutiqueRepository.findById(id).ifPresent(boutique -> {
            String qrCodePath = qrCodeGenerator.generateQRCode("BOUTIQUE_" + boutique.getIdboutique(), 300);
            boutique.setQrcode(qrCodePath);
            boutiqueRepository.save(boutique);
        });
    }
    
    @Override
    public void activate(Integer id) {
        boutiqueRepository.findById(id).ifPresent(b -> {
            b.setEtat("ACTIF");
            boutiqueRepository.save(b);
            securityLogger.logAction("ACTIVATE", "boutique", id.toString());
        });
    }
    
    @Override
    public void deactivate(Integer id) {
        boutiqueRepository.findById(id).ifPresent(b -> {
            b.setEtat("INACTIF");
            boutiqueRepository.save(b);
            securityLogger.logAction("DEACTIVATE", "boutique", id.toString());
        });
    }
}

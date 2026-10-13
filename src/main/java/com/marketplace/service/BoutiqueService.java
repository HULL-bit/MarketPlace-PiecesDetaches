package com.marketplace.service;

import com.marketplace.entity.Boutique;
import java.util.List;
import java.util.Optional;

public interface BoutiqueService {
    Boutique save(Boutique boutique);
    Boutique update(Boutique boutique);
    void delete(Integer id);
    Optional<Boutique> findById(Integer id);
    List<Boutique> findAll();
    List<Boutique> findByEtat(String etat);
    List<Boutique> findByLibelleContaining(String libelle);
    Optional<Boutique> findByQrcode(String qrcode);
    List<Boutique> search(String searchTerm);
    Long countActiveBoutiques();
    void generateQRCode(Integer id);
    void activate(Integer id);
    void deactivate(Integer id);
}

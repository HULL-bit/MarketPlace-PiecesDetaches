package com.marketplace.service;

import com.marketplace.entity.Piece;
import java.util.List;
import java.util.Optional;

public interface PieceService {
    Piece save(Piece piece);
    Piece update(Piece piece);
    void delete(Integer id);
    Optional<Piece> findById(Integer id);
    List<Piece> findAll();

    /** Liste admin avec boutique et catégorie chargées (évite LazyInitializationException en vue). */
    List<Piece> findAllWithBoutiqueAndCategorie();
    List<Piece> findByBoutique(Integer idboutique);
    List<Piece> findByCategorie(Integer idcategorie);
    List<Piece> findByEtat(String etat);
    List<Piece> search(String libelle, Integer categorieId, Integer boutiqueId);
    List<Piece> findAvailable();
    Optional<Piece> findByQrcode(String qrcode);
    Long countAvailable();
    void generateQRCode(Integer id);
    void activate(Integer id);
    void deactivate(Integer id);

    /** Création ou mise à jour depuis l'admin (relations boutique / catégorie par identifiants,
     * prix (achat/vente/tva/réduction) et image incluses). */
    Piece saveOrUpdateFromAdmin(Integer idpiece, String libelle, String ref, String description, String etat,
                                Integer idboutique, Integer idcatpiece, String marque, String modele,
                                String annee, String serie, Integer idpiecemodele, String image,
                                Integer achat, Integer vente, Integer tva, Integer reduction,
                                Integer code, Integer nombre, String couleur, String nouvelleCategorie);
}

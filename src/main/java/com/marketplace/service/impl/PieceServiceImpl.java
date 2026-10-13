package com.marketplace.service.impl;

import com.marketplace.entity.CategoriePiece;
import com.marketplace.entity.DetailPiece;
import com.marketplace.entity.Piece;
import com.marketplace.entity.PieceModele;
import com.marketplace.entity.PrixPiece;
import com.marketplace.repository.BoutiqueRepository;
import com.marketplace.repository.CategoriePieceRepository;
import com.marketplace.repository.DetailPieceRepository;
import com.marketplace.repository.PieceRepository;
import com.marketplace.repository.PieceModeleRepository;
import com.marketplace.repository.PrixPieceRepository;
import com.marketplace.service.PieceService;
import com.marketplace.util.QRCodeGenerator;
import com.marketplace.util.SecurityLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PieceServiceImpl implements PieceService {
    
    private final PieceRepository pieceRepository;
    private final BoutiqueRepository boutiqueRepository;
    private final CategoriePieceRepository categoriePieceRepository;
    private final PieceModeleRepository pieceModeleRepository;
    private final DetailPieceRepository detailPieceRepository;
    private final PrixPieceRepository prixPieceRepository;
    private final QRCodeGenerator qrCodeGenerator;
    private final SecurityLogger securityLogger;

    public PieceServiceImpl(PieceRepository pieceRepository, BoutiqueRepository boutiqueRepository,
                            CategoriePieceRepository categoriePieceRepository, PieceModeleRepository pieceModeleRepository,
                            DetailPieceRepository detailPieceRepository, PrixPieceRepository prixPieceRepository,
                            QRCodeGenerator qrCodeGenerator, SecurityLogger securityLogger) {
        this.pieceRepository = pieceRepository;
        this.boutiqueRepository = boutiqueRepository;
        this.categoriePieceRepository = categoriePieceRepository;
        this.pieceModeleRepository = pieceModeleRepository;
        this.detailPieceRepository = detailPieceRepository;
        this.prixPieceRepository = prixPieceRepository;
        this.qrCodeGenerator = qrCodeGenerator;
        this.securityLogger = securityLogger;
    }

    
    @Override
    public Piece save(Piece piece) {
        Piece saved = pieceRepository.save(piece);
        generateQRCode(saved.getIdpiece());
        securityLogger.logAction("CREATE", "piece", saved.getIdpiece().toString());
        return saved;
    }
    
    @Override
    public Piece update(Piece piece) {
        Piece updated = pieceRepository.save(piece);
        securityLogger.logAction("UPDATE", "piece", updated.getIdpiece().toString());
        return updated;
    }
    
    @Override
    public void delete(Integer id) {
        pieceRepository.deleteById(id);
        securityLogger.logAction("DELETE", "piece", id.toString());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Piece> findById(Integer id) {
        return pieceRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Piece> findAll() {
        return pieceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Piece> findAllWithBoutiqueAndCategorie() {
        return pieceRepository.findAllWithBoutiqueAndCategorie();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Piece> findByBoutique(Integer idboutique) {
        return pieceRepository.findByBoutiqueIdboutique(idboutique);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Piece> findByCategorie(Integer idcategorie) {
        return pieceRepository.findByCategoriePieceIdcatpiece(idcategorie);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Piece> findByEtat(String etat) {
        return pieceRepository.findByEtat(etat);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Piece> search(String libelle, Integer categorieId, Integer boutiqueId) {
        return pieceRepository.searchPieces(libelle, categorieId, boutiqueId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Piece> findAvailable() {
        return pieceRepository.findAllAvailable();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Piece> findByQrcode(String qrcode) {
        return pieceRepository.findByQrcode(qrcode);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long countAvailable() {
        return pieceRepository.countAvailablePieces();
    }
    
    @Override
    public void generateQRCode(Integer id) {
        pieceRepository.findById(id).ifPresent(piece -> {
            String qrCodePath = qrCodeGenerator.generateQRCode("PIECE_" + piece.getIdpiece(), 300);
            piece.setQrcode(qrCodePath);
            pieceRepository.save(piece);
        });
    }
    
    @Override
    public void activate(Integer id) {
        pieceRepository.findById(id).ifPresent(p -> {
            p.setEtat("DISPONIBLE");
            pieceRepository.save(p);
            securityLogger.logAction("ACTIVATE", "piece", id.toString());
        });
    }
    
    @Override
    public void deactivate(Integer id) {
        pieceRepository.findById(id).ifPresent(p -> {
            p.setEtat("INDISPONIBLE");
            pieceRepository.save(p);
            securityLogger.logAction("DEACTIVATE", "piece", id.toString());
        });
    }

    @Override
    public Piece saveOrUpdateFromAdmin(Integer idpiece, String libelle, String ref, String description, String etat,
                                       Integer idboutique, Integer idcatpiece, String marque, String modele,
                                       String annee, String serie, Integer idpiecemodele, String image,
                                       Integer achat, Integer vente, Integer tva, Integer reduction,
                                       Integer code, Integer nombre, String couleur, String nouvelleCategorie) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Piece p;
        boolean isNew = idpiece == null || idpiece <= 0;
        if (!isNew) {
            p = pieceRepository.findById(idpiece).orElseThrow(() -> new IllegalArgumentException("Pièce introuvable"));
            p.setDatemaj(today);
        } else {
            p = new Piece();
            p.setDate(today);
        }
        p.setNombre(nombre != null ? nombre : (isNew ? 0 : p.getNombre()));
        p.setCode(code);
        p.setLibelle(libelle.trim());
        if (ref != null && !ref.isBlank()) {
            p.setRef(ref.trim());
        } else if (isNew) {
            p.setRef(null);
        }
        p.setDescription(description);
        p.setEtat(etat != null && !etat.isBlank() ? etat.trim() : "DISPONIBLE");
        p.setImage(sanitize(image));
        p.setCouleur(sanitize(couleur));

        if (idboutique != null && idboutique > 0) {
            boutiqueRepository.findById(idboutique).ifPresentOrElse(p::setBoutique, () -> p.setBoutique(null));
        } else {
            p.setBoutique(null);
        }
        String nouvelleCategorieV = sanitize(nouvelleCategorie);
        if (nouvelleCategorieV != null) {
            CategoriePiece cat = categoriePieceRepository.findFirstByLibelleIgnoreCase(nouvelleCategorieV)
                    .orElseGet(() -> {
                        CategoriePiece c = new CategoriePiece();
                        c.setLibelle(nouvelleCategorieV);
                        String refBase = nouvelleCategorieV.toUpperCase(java.util.Locale.ROOT).replaceAll("[^A-Z0-9]", "");
                        c.setRef(refBase.isEmpty() ? "CAT" : refBase.substring(0, Math.min(12, refBase.length())));
                        c.setDate(today);
                        c.setNombre(0);
                        return categoriePieceRepository.save(c);
                    });
            p.setCategoriepiece(cat);
        } else if (idcatpiece != null && idcatpiece > 0) {
            categoriePieceRepository.findById(idcatpiece).ifPresentOrElse(p::setCategoriepiece, () -> p.setCategoriepiece(null));
        } else {
            p.setCategoriepiece(null);
        }

        // Resolve PieceModele: prefer direct ID, fallback to text matching
        PieceModele resolved = null;
        if (idpiecemodele != null && idpiecemodele > 0) {
            resolved = pieceModeleRepository.findById(idpiecemodele).orElse(null);
        }
        if (resolved == null) {
            String marqueV = sanitize(marque);
            String modeleV = sanitize(modele);
            String anneeV = sanitize(annee);
            if (marqueV != null || modeleV != null || anneeV != null) {
                String m = marqueV != null ? marqueV : "";
                String mo = modeleV != null ? modeleV : "";
                String a = anneeV != null ? anneeV : "";
                resolved = pieceModeleRepository
                        .findFirstByMarqueIgnoreCaseAndModeleIgnoreCaseAndAnneeIgnoreCase(m, mo, a)
                        .orElseGet(() -> {
                            PieceModele pm = new PieceModele();
                            pm.setMarque(m);
                            pm.setModele(mo);
                            pm.setAnnee(a);
                            pm.setLibelle((m + " " + mo + " " + a).trim());
                            return pieceModeleRepository.save(pm);
                        });
            }
        }
        p.setPiecemodele(resolved);

        Piece saved = isNew ? save(p) : update(p);

        String serieV = sanitize(serie);
        if (serieV != null) {
            DetailPiece detail = detailPieceRepository
                    .findFirstByPieceIdpieceAndCleIgnoreCase(saved.getIdpiece(), "SERIE")
                    .orElseGet(() -> {
                        DetailPiece d = new DetailPiece();
                        d.setPiece(saved);
                        d.setCle("SERIE");
                        return d;
                    });
            detail.setValeur(serieV);
            detailPieceRepository.save(detail);
        }

        if (achat != null || vente != null || tva != null || reduction != null) {
            PrixPiece prix = prixPieceRepository.findTopByPieceIdpieceOrderByDateDesc(saved.getIdpiece())
                    .orElseGet(() -> {
                        PrixPiece pp = new PrixPiece();
                        pp.setPiece(saved);
                        pp.setDate(today);
                        return pp;
                    });
            if (achat != null) {
                prix.setAchat(achat);
            }
            if (vente != null) {
                prix.setVente(vente);
            }
            if (tva != null) {
                prix.setTva(tva);
            }
            if (reduction != null) {
                prix.setReduction(reduction);
            }
            prix.setDatemaj(today);
            prixPieceRepository.save(prix);
        }

        return saved;
    }

    private static String sanitize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}

package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "piece")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Piece implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpiece")
    private Integer idpiece;
    
    @Column(name = "libelle", nullable = false, length = 300)
    private String libelle;
    
    @Column(name = "code")
    private Integer code;
    
    @Column(name = "ref", length = 100)
    private String ref;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "nombre", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer nombre = 0;
    
    @Column(name = "creepar")
    private Integer creepar;
    
    @Column(name = "etat", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'DISPONIBLE'")
    @Builder.Default
    private String etat = "DISPONIBLE";
    
    @Column(name = "likes", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer likes = 0;
    
    @Column(name = "unlikes", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer unlikes = 0;
    
    @Column(name = "vie", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer vie = 0;
    
    @Column(name = "qrcode", length = 500)
    private String qrcode;
    
    @Column(name = "image", length = 500)
    private String image;
    
    @Column(name = "video")
    private Integer video;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "couleur", length = 50)
    private String couleur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idboutique")
    private Boutique boutique;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcatpiece")
    private CategoriePiece categoriePiece;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpiecemodele")
    private PieceModele pieceModele;
    
    @OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PrixPiece> prix;
    
    @OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ImageVideoPiece> medias;
    
    @OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AvisPiece> avis;
    
    @OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetailPiece> details;
    
    @OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LignePanier> lignesPanier;
    
    @OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lot> lots;
    
    @OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommande> lignesCommande;
    
    @OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PiecePromotion> piecePromotions;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdpiece() { return idpiece; }
    public void setIdpiece(Integer idpiece) { this.idpiece = idpiece; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public Integer getNombre() { return nombre; }
    public void setNombre(Integer nombre) { this.nombre = nombre; }
    public Integer getCreepar() { return creepar; }
    public void setCreepar(Integer creepar) { this.creepar = creepar; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Integer getLikes() { return likes; }
    public void setLikes(Integer likes) { this.likes = likes; }
    public Integer getUnlikes() { return unlikes; }
    public void setUnlikes(Integer unlikes) { this.unlikes = unlikes; }
    public Integer getVie() { return vie; }
    public void setVie(Integer vie) { this.vie = vie; }
    public String getQrcode() { return qrcode; }
    public void setQrcode(String qrcode) { this.qrcode = qrcode; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public Integer getVideo() { return video; }
    public void setVideo(Integer video) { this.video = video; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCouleur() { return couleur; }
    public void setCouleur(String couleur) { this.couleur = couleur; }
    public Boutique getBoutique() { return boutique; }
    public void setBoutique(Boutique boutique) { this.boutique = boutique; }
    public CategoriePiece getCategoriepiece() { return categoriePiece; }
    public void setCategoriepiece(CategoriePiece categoriePiece) { this.categoriePiece = categoriePiece; }
    public PieceModele getPiecemodele() { return pieceModele; }
    public void setPiecemodele(PieceModele pieceModele) { this.pieceModele = pieceModele; }
    public List<PrixPiece> getPrix() { return prix; }
    public void setPrix(List<PrixPiece> prix) { this.prix = prix; }
    public List<ImageVideoPiece> getMedias() { return medias; }
    public void setMedias(List<ImageVideoPiece> medias) { this.medias = medias; }
    public List<AvisPiece> getAvis() { return avis; }
    public void setAvis(List<AvisPiece> avis) { this.avis = avis; }
    public List<DetailPiece> getDetails() { return details; }
    public void setDetails(List<DetailPiece> details) { this.details = details; }
    public List<LignePanier> getLignespanier() { return lignesPanier; }
    public void setLignespanier(List<LignePanier> lignesPanier) { this.lignesPanier = lignesPanier; }
    public List<Lot> getLots() { return lots; }
    public void setLots(List<Lot> lots) { this.lots = lots; }
    public List<LigneCommande> getLignescommande() { return lignesCommande; }
    public void setLignescommande(List<LigneCommande> lignesCommande) { this.lignesCommande = lignesCommande; }
    public List<PiecePromotion> getPiecepromotions() { return piecePromotions; }
    public void setPiecepromotions(List<PiecePromotion> piecePromotions) { this.piecePromotions = piecePromotions; }

    /**
     * Numéro de série / VIN, stocké en clé-valeur dans DetailPiece (cle="SERIE").
     */
    public String getSerie() {
        if (details == null) {
            return null;
        }
        for (DetailPiece d : details) {
            if (d.getCle() != null && d.getCle().equalsIgnoreCase("SERIE")) {
                return d.getValeur();
            }
        }
        return null;
    }
}

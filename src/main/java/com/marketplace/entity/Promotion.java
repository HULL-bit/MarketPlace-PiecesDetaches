package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "promotion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpromo")
    private Integer idpromo;
    
    @Column(name = "libelle", length = 200)
    private String libelle;
    
    @Column(name = "codepromo", length = 50)
    private String codepromo;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datedebut", length = 20)
    private String datedebut;
    
    @Column(name = "datefin", length = 20)
    private String datefin;
    
    @Column(name = "taux", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer taux = 0;
    
    @Column(name = "nombrearticles", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer nombrearticles = 0;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @Column(name = "qrcode", length = 500)
    private String qrcode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idboutique")
    private Boutique boutique;
    
    @OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PiecePromotion> piecePromotions;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdpromo() { return idpromo; }
    public void setIdpromo(Integer idpromo) { this.idpromo = idpromo; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public String getCodepromo() { return codepromo; }
    public void setCodepromo(String codepromo) { this.codepromo = codepromo; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatedebut() { return datedebut; }
    public void setDatedebut(String datedebut) { this.datedebut = datedebut; }
    public String getDatefin() { return datefin; }
    public void setDatefin(String datefin) { this.datefin = datefin; }
    public Integer getTaux() { return taux; }
    public void setTaux(Integer taux) { this.taux = taux; }
    public Integer getNombrearticles() { return nombrearticles; }
    public void setNombrearticles(Integer nombrearticles) { this.nombrearticles = nombrearticles; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public String getQrcode() { return qrcode; }
    public void setQrcode(String qrcode) { this.qrcode = qrcode; }
    public Boutique getBoutique() { return boutique; }
    public void setBoutique(Boutique boutique) { this.boutique = boutique; }
    public List<PiecePromotion> getPiecepromotions() { return piecePromotions; }
    public void setPiecepromotions(List<PiecePromotion> piecePromotions) { this.piecePromotions = piecePromotions; }
}

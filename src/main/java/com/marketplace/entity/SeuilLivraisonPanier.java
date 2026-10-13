package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "seuil_livraison_panier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeuilLivraisonPanier implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idseuillivpan")
    private Integer idseuillivpan;
    
    @Column(name = "libelle", length = 200)
    private String libelle;
    
    @Column(name = "montantmin")
    private Integer montantmin;
    
    @Column(name = "montantmax")
    private Integer montantmax;
    
    @Column(name = "livraisonofferte", columnDefinition = "BOOLEAN DEFAULT false")
    @Builder.Default
    private Boolean livraisonofferte = false;
    
    @Column(name = "reduction", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer reduction = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idboutique")
    private Boutique boutique;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdseuillivpan() { return idseuillivpan; }
    public void setIdseuillivpan(Integer idseuillivpan) { this.idseuillivpan = idseuillivpan; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public Integer getMontantmin() { return montantmin; }
    public void setMontantmin(Integer montantmin) { this.montantmin = montantmin; }
    public Integer getMontantmax() { return montantmax; }
    public void setMontantmax(Integer montantmax) { this.montantmax = montantmax; }
    public Boolean getLivraisonofferte() { return livraisonofferte; }
    public void setLivraisonofferte(Boolean livraisonofferte) { this.livraisonofferte = livraisonofferte; }
    public Integer getReduction() { return reduction; }
    public void setReduction(Integer reduction) { this.reduction = reduction; }
    public Boutique getBoutique() { return boutique; }
    public void setBoutique(Boutique boutique) { this.boutique = boutique; }
}

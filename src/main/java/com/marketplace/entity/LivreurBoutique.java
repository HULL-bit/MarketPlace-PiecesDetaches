package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "livreur_boutique")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(LivreurBoutiqueId.class)
public class LivreurBoutique implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idlivreur")
    private Livreur livreur;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idboutique")
    private Boutique boutique;
    
    @Column(name = "dateaffectation", length = 20)
    private String dateaffectation;
    
    @Column(name = "etat", length = 20)
    private String etat;

    // Getters and Setters (generated for Eclipse compatibility)
    public Livreur getLivreur() { return livreur; }
    public void setLivreur(Livreur livreur) { this.livreur = livreur; }
    public Boutique getBoutique() { return boutique; }
    public void setBoutique(Boutique boutique) { this.boutique = boutique; }
    public String getDateaffectation() { return dateaffectation; }
    public void setDateaffectation(String dateaffectation) { this.dateaffectation = dateaffectation; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
}

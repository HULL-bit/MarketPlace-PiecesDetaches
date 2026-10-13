package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "facture")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facture implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfacture")
    private Integer idfacture;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @Column(name = "montant")
    private Integer montant;
    
    @Column(name = "remise", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer remise = 0;
    
    @Column(name = "creepar")
    private Integer creepar;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcommande")
    private Commande commande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idtypefacture")
    private TypeFacture typeFacture;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdfacture() { return idfacture; }
    public void setIdfacture(Integer idfacture) { this.idfacture = idfacture; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Integer getMontant() { return montant; }
    public void setMontant(Integer montant) { this.montant = montant; }
    public Integer getRemise() { return remise; }
    public void setRemise(Integer remise) { this.remise = remise; }
    public Integer getCreepar() { return creepar; }
    public void setCreepar(Integer creepar) { this.creepar = creepar; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public TypeFacture getTypefacture() { return typeFacture; }
    public void setTypefacture(TypeFacture typeFacture) { this.typeFacture = typeFacture; }
}

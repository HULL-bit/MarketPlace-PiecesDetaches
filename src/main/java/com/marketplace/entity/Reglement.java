package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "reglement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reglement implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreglement")
    private Integer idreglement;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "montant")
    private Integer montant;
    
    @Column(name = "mode", length = 50)
    private String mode;
    
    @Column(name = "statut", length = 20)
    private String statut;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcommande")
    private Commande commande;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdreglement() { return idreglement; }
    public void setIdreglement(Integer idreglement) { this.idreglement = idreglement; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public Integer getMontant() { return montant; }
    public void setMontant(Integer montant) { this.montant = montant; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
}

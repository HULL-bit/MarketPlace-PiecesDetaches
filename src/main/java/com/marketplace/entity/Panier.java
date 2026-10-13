package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "panier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Panier implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpanier")
    private Integer idpanier;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "nombrearticles", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer nombrearticles = 0;
    
    @Column(name = "etat", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'EN_COURS'")
    @Builder.Default
    private String etat = "EN_COURS";
    
    @Column(name = "prix", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer prix = 0;
    
    @Column(name = "reduction", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer reduction = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idclient")
    private Client client;
    
    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LignePanier> lignes;
    
    @OneToOne(mappedBy = "panier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Commande commande;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdpanier() { return idpanier; }
    public void setIdpanier(Integer idpanier) { this.idpanier = idpanier; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public Integer getNombrearticles() { return nombrearticles; }
    public void setNombrearticles(Integer nombrearticles) { this.nombrearticles = nombrearticles; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Integer getPrix() { return prix; }
    public void setPrix(Integer prix) { this.prix = prix; }
    public Integer getReduction() { return reduction; }
    public void setReduction(Integer reduction) { this.reduction = reduction; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public List<LignePanier> getLignes() { return lignes; }
    public void setLignes(List<LignePanier> lignes) { this.lignes = lignes; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
}

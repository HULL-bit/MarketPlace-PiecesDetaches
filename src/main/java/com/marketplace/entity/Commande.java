package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "commande")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commande implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcommande")
    private Integer idcommande;
    
    @Column(name = "code")
    private Integer code;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "nombrearticle", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer nombrearticle = 0;
    
    @Column(name = "etat", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'EN_ATTENTE'")
    @Builder.Default
    private String etat = "EN_ATTENTE";
    
    @Column(name = "creepar")
    private Integer creepar;
    
    @Column(name = "modelivraison", length = 100)
    private String modelivraison;
    
    @Column(name = "qrcode", length = 500)
    private String qrcode;
    
    @Column(name = "annee", length = 10)
    private String annee;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idclient")
    private Client client;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpanier")
    private Panier panier;
    
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommande> lignes;
    
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reglement> reglements;
    
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Facture> factures;
    
    @OneToOne(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Livraison livraison;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdcommande() { return idcommande; }
    public void setIdcommande(Integer idcommande) { this.idcommande = idcommande; }
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public Integer getNombrearticle() { return nombrearticle; }
    public void setNombrearticle(Integer nombrearticle) { this.nombrearticle = nombrearticle; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Integer getCreepar() { return creepar; }
    public void setCreepar(Integer creepar) { this.creepar = creepar; }
    public String getModelivraison() { return modelivraison; }
    public void setModelivraison(String modelivraison) { this.modelivraison = modelivraison; }
    public String getQrcode() { return qrcode; }
    public void setQrcode(String qrcode) { this.qrcode = qrcode; }
    public String getAnnee() { return annee; }
    public void setAnnee(String annee) { this.annee = annee; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public Panier getPanier() { return panier; }
    public void setPanier(Panier panier) { this.panier = panier; }
    public List<LigneCommande> getLignes() { return lignes; }
    public void setLignes(List<LigneCommande> lignes) { this.lignes = lignes; }
    public List<Reglement> getReglements() { return reglements; }
    public void setReglements(List<Reglement> reglements) { this.reglements = reglements; }
    public List<Facture> getFactures() { return factures; }
    public void setFactures(List<Facture> factures) { this.factures = factures; }
    public Livraison getLivraison() { return livraison; }
    public void setLivraison(Livraison livraison) { this.livraison = livraison; }
}

package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "livraison")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livraison implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlivraison")
    private Integer idlivraison;
    
    @Column(name = "libellcodee")
    private Integer libellcodee;
    
    @Column(name = "ref", length = 100)
    private String ref;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "heure", length = 10)
    private String heure;
    
    @Column(name = "adresse", columnDefinition = "TEXT")
    private String adresse;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @Column(name = "annee", length = 10)
    private String annee;
    
    @Column(name = "mois", length = 10)
    private String mois;
    
    @Column(name = "jour", length = 20)
    private String jour;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcommande")
    private Commande commande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idlivreur")
    private Livreur livreur;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idzone")
    private ZoneLivraison zone;
    
    @OneToMany(mappedBy = "livraison", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AssoEtapeLivraison> etapes;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdlivraison() { return idlivraison; }
    public void setIdlivraison(Integer idlivraison) { this.idlivraison = idlivraison; }
    public Integer getLibellcodee() { return libellcodee; }
    public void setLibellcodee(Integer libellcodee) { this.libellcodee = libellcodee; }
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public String getHeure() { return heure; }
    public void setHeure(String heure) { this.heure = heure; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public String getAnnee() { return annee; }
    public void setAnnee(String annee) { this.annee = annee; }
    public String getMois() { return mois; }
    public void setMois(String mois) { this.mois = mois; }
    public String getJour() { return jour; }
    public void setJour(String jour) { this.jour = jour; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public Livreur getLivreur() { return livreur; }
    public void setLivreur(Livreur livreur) { this.livreur = livreur; }
    public ZoneLivraison getZone() { return zone; }
    public void setZone(ZoneLivraison zone) { this.zone = zone; }
    public List<AssoEtapeLivraison> getEtapes() { return etapes; }
    public void setEtapes(List<AssoEtapeLivraison> etapes) { this.etapes = etapes; }
}

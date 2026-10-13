package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "livreur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livreur implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlivreur")
    private Integer idlivreur;
    
    @Column(name = "code")
    private Integer code;
    
    @Column(name = "ref", length = 100)
    private String ref;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersonne")
    private Personne personne;
    
    @OneToMany(mappedBy = "livreur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LivreurBoutique> livreurBoutiques;
    
    @OneToMany(mappedBy = "livreur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LivreurMoyenTransport> moyensTransport;
    
    @OneToMany(mappedBy = "livreur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Livraison> livraisons;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdlivreur() { return idlivreur; }
    public void setIdlivreur(Integer idlivreur) { this.idlivreur = idlivreur; }
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Personne getPersonne() { return personne; }
    public void setPersonne(Personne personne) { this.personne = personne; }
    public List<LivreurBoutique> getLivreurboutiques() { return livreurBoutiques; }
    public void setLivreurboutiques(List<LivreurBoutique> livreurBoutiques) { this.livreurBoutiques = livreurBoutiques; }
    public List<LivreurMoyenTransport> getMoyenstransport() { return moyensTransport; }
    public void setMoyenstransport(List<LivreurMoyenTransport> moyensTransport) { this.moyensTransport = moyensTransport; }
    public List<Livraison> getLivraisons() { return livraisons; }
    public void setLivraisons(List<Livraison> livraisons) { this.livraisons = livraisons; }
}

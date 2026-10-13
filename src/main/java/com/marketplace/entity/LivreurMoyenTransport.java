package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "livreur_moyen_transport")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivreurMoyenTransport implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmoytransport")
    private Integer idmoytransport;
    
    @Column(name = "type", length = 100)
    private String type;
    
    @Column(name = "libelle", length = 200)
    private String libelle;
    
    @Column(name = "matricule", length = 100)
    private String matricule;
    
    @Column(name = "couleur", length = 50)
    private String couleur;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idlivreur")
    private Livreur livreur;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdmoytransport() { return idmoytransport; }
    public void setIdmoytransport(Integer idmoytransport) { this.idmoytransport = idmoytransport; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public String getCouleur() { return couleur; }
    public void setCouleur(String couleur) { this.couleur = couleur; }
    public Livreur getLivreur() { return livreur; }
    public void setLivreur(Livreur livreur) { this.livreur = livreur; }
}

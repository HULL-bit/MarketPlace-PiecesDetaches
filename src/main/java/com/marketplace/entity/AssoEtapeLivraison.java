package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "asso_etape_livraison")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssoEtapeLivraison implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idasso")
    private Integer idasso;
    
    @Column(name = "libelle", length = 200)
    private String libelle;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idlivraison")
    private Livraison livraison;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdasso() { return idasso; }
    public void setIdasso(Integer idasso) { this.idasso = idasso; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Livraison getLivraison() { return livraison; }
    public void setLivraison(Livraison livraison) { this.livraison = livraison; }
}

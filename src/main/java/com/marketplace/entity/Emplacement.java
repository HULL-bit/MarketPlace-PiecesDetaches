package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "emplacement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emplacement implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idemplacement")
    private Integer idemplacement;
    
    @Column(name = "pays", length = 100)
    private String pays;
    
    @Column(name = "ville", length = 100)
    private String ville;
    
    @Column(name = "quartier", length = 100)
    private String quartier;
    
    @Column(name = "localisation", length = 500)
    private String localisation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idboutique")
    private Boutique boutique;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdemplacement() { return idemplacement; }
    public void setIdemplacement(Integer idemplacement) { this.idemplacement = idemplacement; }
    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public String getQuartier() { return quartier; }
    public void setQuartier(String quartier) { this.quartier = quartier; }
    public String getLocalisation() { return localisation; }
    public void setLocalisation(String localisation) { this.localisation = localisation; }
    public Boutique getBoutique() { return boutique; }
    public void setBoutique(Boutique boutique) { this.boutique = boutique; }
}

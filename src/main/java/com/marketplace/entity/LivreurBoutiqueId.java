package com.marketplace.entity;

import lombok.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LivreurBoutiqueId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer livreur;
    private Integer boutique;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getLivreur() { return livreur; }
    public void setLivreur(Integer livreur) { this.livreur = livreur; }
    public Integer getBoutique() { return boutique; }
    public void setBoutique(Integer boutique) { this.boutique = boutique; }
}

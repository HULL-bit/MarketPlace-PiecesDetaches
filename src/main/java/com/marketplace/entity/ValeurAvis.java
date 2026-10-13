package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "valeur_avis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValeurAvis implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idvaleuravis")
    private Integer idvaleuravis;
    
    @Column(name = "libelle", length = 200)
    private String libelle;
    
    @Column(name = "valeur", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer valeur = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idavispiece")
    private AvisPiece avisPiece;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdvaleuravis() { return idvaleuravis; }
    public void setIdvaleuravis(Integer idvaleuravis) { this.idvaleuravis = idvaleuravis; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public Integer getValeur() { return valeur; }
    public void setValeur(Integer valeur) { this.valeur = valeur; }
    public AvisPiece getAvispiece() { return avisPiece; }
    public void setAvispiece(AvisPiece avisPiece) { this.avisPiece = avisPiece; }
}

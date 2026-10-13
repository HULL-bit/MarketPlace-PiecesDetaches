package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "piece_modele")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PieceModele implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpiecemodele")
    private Integer idpiecemodele;
    
    @Column(name = "libelle", length = 200)
    private String libelle;
    
    @Column(name = "marque", length = 100)
    private String marque;
    
    @Column(name = "modele", length = 100)
    private String modele;
    
    @Column(name = "annee", length = 20)
    private String annee;
    
    @OneToMany(mappedBy = "pieceModele", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Piece> pieces;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdpiecemodele() { return idpiecemodele; }
    public void setIdpiecemodele(Integer idpiecemodele) { this.idpiecemodele = idpiecemodele; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }
    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }
    public String getAnnee() { return annee; }
    public void setAnnee(String annee) { this.annee = annee; }
    public List<Piece> getPieces() { return pieces; }
    public void setPieces(List<Piece> pieces) { this.pieces = pieces; }
}

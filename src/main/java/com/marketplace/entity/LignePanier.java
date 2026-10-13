package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "ligne_panier")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LignePanier implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlignepanier")
    private Integer idlignepanier;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "quantite", columnDefinition = "INTEGER DEFAULT 1")
    @Builder.Default
    private Integer quantite = 1;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpanier")
    private Panier panier;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpiece")
    private Piece piece;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdlignepanier() { return idlignepanier; }
    public void setIdlignepanier(Integer idlignepanier) { this.idlignepanier = idlignepanier; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Panier getPanier() { return panier; }
    public void setPanier(Panier panier) { this.panier = panier; }
    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
}

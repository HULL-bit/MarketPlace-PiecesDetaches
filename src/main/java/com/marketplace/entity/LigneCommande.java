package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "ligne_commande")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LigneCommande implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlignecommand")
    private Integer idlignecommand;
    
    @Column(name = "quantite", columnDefinition = "INTEGER DEFAULT 1")
    @Builder.Default
    private Integer quantite = 1;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "prix")
    private Integer prix;
    
    @Column(name = "remise", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer remise = 0;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcommande")
    private Commande commande;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpiece")
    private Piece piece;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdlignecommand() { return idlignecommand; }
    public void setIdlignecommand(Integer idlignecommand) { this.idlignecommand = idlignecommand; }
    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public Integer getPrix() { return prix; }
    public void setPrix(Integer prix) { this.prix = prix; }
    public Integer getRemise() { return remise; }
    public void setRemise(Integer remise) { this.remise = remise; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
}

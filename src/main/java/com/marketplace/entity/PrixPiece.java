package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "prix_piece")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrixPiece implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprixpiece")
    private Integer idprixpiece;
    
    @Column(name = "achat")
    private Integer achat;
    
    @Column(name = "vente")
    private Integer vente;
    
    @Column(name = "ventereduit")
    private Integer ventereduit;
    
    @Column(name = "tva", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer tva = 0;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "reduction", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer reduction = 0;
    
    @Column(name = "promotion", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer promotion = 0;
    
    @Column(name = "seuilachatpromo", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer seuilachatpromo = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpiece")
    private Piece piece;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdprixpiece() { return idprixpiece; }
    public void setIdprixpiece(Integer idprixpiece) { this.idprixpiece = idprixpiece; }
    public Integer getAchat() { return achat; }
    public void setAchat(Integer achat) { this.achat = achat; }
    public Integer getVente() { return vente; }
    public void setVente(Integer vente) { this.vente = vente; }
    public Integer getVentereduit() { return ventereduit; }
    public void setVentereduit(Integer ventereduit) { this.ventereduit = ventereduit; }
    public Integer getTva() { return tva; }
    public void setTva(Integer tva) { this.tva = tva; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public Integer getReduction() { return reduction; }
    public void setReduction(Integer reduction) { this.reduction = reduction; }
    public Integer getPromotion() { return promotion; }
    public void setPromotion(Integer promotion) { this.promotion = promotion; }
    public Integer getSeuilachatpromo() { return seuilachatpromo; }
    public void setSeuilachatpromo(Integer seuilachatpromo) { this.seuilachatpromo = seuilachatpromo; }
    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
}

package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "detail_piece")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailPiece implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddetail")
    private Integer iddetail;
    
    @Column(name = "cle", length = 100)
    private String cle;
    
    @Column(name = "valeur", length = 500)
    private String valeur;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpiece")
    private Piece piece;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIddetail() { return iddetail; }
    public void setIddetail(Integer iddetail) { this.iddetail = iddetail; }
    public String getCle() { return cle; }
    public void setCle(String cle) { this.cle = cle; }
    public String getValeur() { return valeur; }
    public void setValeur(String valeur) { this.valeur = valeur; }
    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
}

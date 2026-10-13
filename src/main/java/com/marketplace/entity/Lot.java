package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "lot")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lot implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlot")
    private Integer idlot;
    
    @Column(name = "libelle", length = 300)
    private String libelle;
    
    @Column(name = "code")
    private Integer code;
    
    @Column(name = "ref", length = 100)
    private String ref;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @Column(name = "nombrepieces", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer nombrepieces = 0;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "creepar")
    private Integer creepar;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idstock")
    private Stock stock;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpiece")
    private Piece piece;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdlot() { return idlot; }
    public void setIdlot(Integer idlot) { this.idlot = idlot; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Integer getNombrepieces() { return nombrepieces; }
    public void setNombrepieces(Integer nombrepieces) { this.nombrepieces = nombrepieces; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public Integer getCreepar() { return creepar; }
    public void setCreepar(Integer creepar) { this.creepar = creepar; }
    public Stock getStock() { return stock; }
    public void setStock(Stock stock) { this.stock = stock; }
    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
}

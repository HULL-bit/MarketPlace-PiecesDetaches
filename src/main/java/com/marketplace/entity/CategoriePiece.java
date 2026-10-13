package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categorie_piece")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriePiece implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcatpiece")
    private Integer idcatpiece;
    
    @Column(name = "libelle", nullable = false, length = 300)
    private String libelle;
    
    @Column(name = "code")
    private Integer code;
    
    @Column(name = "ref", length = 100)
    private String ref;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "nombre", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer nombre = 0;
    
    @Column(name = "creepar")
    private Integer creepar;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CategoriePiece parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CategoriePiece> sousCategories;
    
    @OneToMany(mappedBy = "categoriePiece", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Piece> pieces;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdcatpiece() { return idcatpiece; }
    public void setIdcatpiece(Integer idcatpiece) { this.idcatpiece = idcatpiece; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public Integer getNombre() { return nombre; }
    public void setNombre(Integer nombre) { this.nombre = nombre; }
    public Integer getCreepar() { return creepar; }
    public void setCreepar(Integer creepar) { this.creepar = creepar; }
    public CategoriePiece getParent() { return parent; }
    public void setParent(CategoriePiece parent) { this.parent = parent; }
    public List<CategoriePiece> getSouscategories() { return sousCategories; }
    public void setSouscategories(List<CategoriePiece> sousCategories) { this.sousCategories = sousCategories; }
    public List<Piece> getPieces() { return pieces; }
    public void setPieces(List<Piece> pieces) { this.pieces = pieces; }
}

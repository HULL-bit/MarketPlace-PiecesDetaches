package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "type_facture")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeFacture implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtypefacture")
    private Integer idtypefacture;
    
    @Column(name = "libelle", length = 200)
    private String libelle;
    
    @OneToMany(mappedBy = "typeFacture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Facture> factures;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdtypefacture() { return idtypefacture; }
    public void setIdtypefacture(Integer idtypefacture) { this.idtypefacture = idtypefacture; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public List<Facture> getFactures() { return factures; }
    public void setFactures(List<Facture> factures) { this.factures = factures; }
}

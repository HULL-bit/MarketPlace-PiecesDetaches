package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "zone_livraison")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZoneLivraison implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idzone")
    private Integer idzone;
    
    @Column(name = "libelle", length = 200)
    private String libelle;
    
    @Column(name = "code")
    private Integer code;
    
    @Column(name = "ref", length = 100)
    private String ref;
    
    @Column(name = "tarif", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer tarif = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idboutique")
    private Boutique boutique;
    
    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Livraison> livraisons;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdzone() { return idzone; }
    public void setIdzone(Integer idzone) { this.idzone = idzone; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    public String getRef() { return ref; }
    public void setRef(String ref) { this.ref = ref; }
    public Integer getTarif() { return tarif; }
    public void setTarif(Integer tarif) { this.tarif = tarif; }
    public Boutique getBoutique() { return boutique; }
    public void setBoutique(Boutique boutique) { this.boutique = boutique; }
    public List<Livraison> getLivraisons() { return livraisons; }
    public void setLivraisons(List<Livraison> livraisons) { this.livraisons = livraisons; }
}

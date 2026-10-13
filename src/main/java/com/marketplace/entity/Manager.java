package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "manager")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manager implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmanager")
    private Integer idmanager;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersonne")
    private Personne personne;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idboutique")
    private Boutique boutique;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdmanager() { return idmanager; }
    public void setIdmanager(Integer idmanager) { this.idmanager = idmanager; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Personne getPersonne() { return personne; }
    public void setPersonne(Personne personne) { this.personne = personne; }
    public Boutique getBoutique() { return boutique; }
    public void setBoutique(Boutique boutique) { this.boutique = boutique; }
}

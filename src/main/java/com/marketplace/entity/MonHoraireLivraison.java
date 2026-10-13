package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "mon_horaire_livraison")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonHoraireLivraison implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmonhorliv")
    private Integer idmonhorliv;
    
    @Column(name = "libelle", length = 100)
    private String libelle;
    
    @Column(name = "datedebut", length = 20)
    private String datedebut;
    
    @Column(name = "datefin", length = 20)
    private String datefin;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idclient")
    private Client client;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdmonhorliv() { return idmonhorliv; }
    public void setIdmonhorliv(Integer idmonhorliv) { this.idmonhorliv = idmonhorliv; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public String getDatedebut() { return datedebut; }
    public void setDatedebut(String datedebut) { this.datedebut = datedebut; }
    public String getDatefin() { return datefin; }
    public void setDatefin(String datefin) { this.datefin = datefin; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
}

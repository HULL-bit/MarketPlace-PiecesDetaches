package com.marketplace.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "periode_config")
public class PeriodeConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idperiode")
    private Integer idperiode;

    @Column(name = "type", length = 20, nullable = false)
    private String type; // ANNEE, MOIS, JOUR

    @Column(name = "nom", length = 200, nullable = false)
    private String nom;

    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @Column(name = "etat", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'ACTIF'")
    private String etat = "ACTIF";

    @Column(name = "ordre", columnDefinition = "INTEGER DEFAULT 0")
    private Integer ordre = 0;

    @Column(name = "date", length = 20)
    private String date;

    @Column(name = "datemaj", length = 20)
    private String datemaj;

    public Integer getIdperiode() { return idperiode; }
    public void setIdperiode(Integer idperiode) { this.idperiode = idperiode; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Integer getOrdre() { return ordre; }
    public void setOrdre(Integer ordre) { this.ordre = ordre; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
}


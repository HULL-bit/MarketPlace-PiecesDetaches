package com.marketplace.dto;

import java.io.Serializable;

/**
 * Ligne de panier visiteur (session HTTP, sans compte obligatoire).
 */
public class GuestCartLine implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idpiece;
    private String libelle;
    private String ref;
    private int quantite;
    private int prixUnitaire;

    public GuestCartLine() {
    }

    public GuestCartLine(Integer idpiece, String libelle, String ref, int quantite, int prixUnitaire) {
        this.idpiece = idpiece;
        this.libelle = libelle;
        this.ref = ref;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getIdpiece() {
        return idpiece;
    }

    public void setIdpiece(Integer idpiece) {
        this.idpiece = idpiece;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(int prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public int getSousTotal() {
        return prixUnitaire * quantite;
    }
}

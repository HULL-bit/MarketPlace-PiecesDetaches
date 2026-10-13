package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "permission_utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PermissionUtilisateurId.class)
public class PermissionUtilisateur implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersonne")
    private Personne personne;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpermission")
    private Permission permission;

    @Column(name = "date", length = 20)
    private String date;

    @Column(name = "etat", length = 20)
    private String etat;

    // Getters and Setters (generated for Eclipse compatibility)
    public Personne getPersonne() { return personne; }
    public void setPersonne(Personne personne) { this.personne = personne; }
    public Permission getPermission() { return permission; }
    public void setPermission(Permission permission) { this.permission = permission; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
}

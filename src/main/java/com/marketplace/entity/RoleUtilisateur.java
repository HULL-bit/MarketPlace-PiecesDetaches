package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "role_utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(RoleUtilisateurId.class)
public class RoleUtilisateur implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpersonne")
    private Personne personne;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprofile")
    private Profile profile;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "nombreinactif", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer nombreinactif = 0;
    
    @Column(name = "etat", length = 20)
    private String etat;
    
    @Column(name = "clos", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer clos = 0;
    
    @Column(name = "datecloture", length = 20)
    private String datecloture;
    
    @Column(name = "type", length = 50)
    private String type;

    // Getters and Setters (generated for Eclipse compatibility)
    public Personne getPersonne() { return personne; }
    public void setPersonne(Personne personne) { this.personne = personne; }
    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public Integer getNombreinactif() { return nombreinactif; }
    public void setNombreinactif(Integer nombreinactif) { this.nombreinactif = nombreinactif; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Integer getClos() { return clos; }
    public void setClos(Integer clos) { this.clos = clos; }
    public String getDatecloture() { return datecloture; }
    public void setDatecloture(String datecloture) { this.datecloture = datecloture; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}

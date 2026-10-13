package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "idprofile", length = 50)
    private String idprofile;
    
    @Column(name = "libelle", nullable = false, length = 200)
    private String libelle;
    
    @Column(name = "etat", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'ACTIF'")
    @Builder.Default
    private String etat = "ACTIF";
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "nombre", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer nombre = 0;
    
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoleUtilisateur> roleUtilisateurs;
    
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoleProfile> roleProfiles;

    // Getters and Setters (generated for Eclipse compatibility)
    public String getIdprofile() { return idprofile; }
    public void setIdprofile(String idprofile) { this.idprofile = idprofile; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDatemaj() { return datemaj; }
    public void setDatemaj(String datemaj) { this.datemaj = datemaj; }
    public Integer getNombre() { return nombre; }
    public void setNombre(Integer nombre) { this.nombre = nombre; }
    public List<RoleUtilisateur> getRoleutilisateurs() { return roleUtilisateurs; }
    public void setRoleutilisateurs(List<RoleUtilisateur> roleUtilisateurs) { this.roleUtilisateurs = roleUtilisateurs; }
    public List<RoleProfile> getRoleprofiles() { return roleProfiles; }
    public void setRoleprofiles(List<RoleProfile> roleProfiles) { this.roleProfiles = roleProfiles; }
}

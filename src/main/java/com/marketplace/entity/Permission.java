package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpermission")
    private Integer idpermission;
    
    @Column(name = "libelle", length = 200)
    private String libelle;
    
    @Column(name = "code")
    private Integer code;
    
    @Column(name = "ref", length = 100)
    private String ref;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "datemaj", length = 20)
    private String datemaj;
    
    @Column(name = "etat", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'ACTIF'")
    @Builder.Default
    private String etat = "ACTIF";
    
    @Column(name = "nombre", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer nombre = 0;
    
    @Column(name = "niveau", columnDefinition = "INTEGER DEFAULT 0")
    @Builder.Default
    private Integer niveau = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idbranche")
    private BranchePermission branche;
    
    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RoleProfile> roleProfiles;

    // Getters and Setters (generated for Eclipse compatibility)
    public Integer getIdpermission() { return idpermission; }
    public void setIdpermission(Integer idpermission) { this.idpermission = idpermission; }
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
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public Integer getNombre() { return nombre; }
    public void setNombre(Integer nombre) { this.nombre = nombre; }
    public Integer getNiveau() { return niveau; }
    public void setNiveau(Integer niveau) { this.niveau = niveau; }
    public BranchePermission getBranche() { return branche; }
    public void setBranche(BranchePermission branche) { this.branche = branche; }
    public List<RoleProfile> getRoleprofiles() { return roleProfiles; }
    public void setRoleprofiles(List<RoleProfile> roleProfiles) { this.roleProfiles = roleProfiles; }
}

package com.marketplace.entity;

import javax.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "role_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(RoleProfileId.class)
public class RoleProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprofile")
    private Profile profile;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idpermission")
    private Permission permission;
    
    @Column(name = "date", length = 20)
    private String date;
    
    @Column(name = "etat", length = 20)
    private String etat;

    // Getters and Setters (generated for Eclipse compatibility)
    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }
    public Permission getPermission() { return permission; }
    public void setPermission(Permission permission) { this.permission = permission; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
}

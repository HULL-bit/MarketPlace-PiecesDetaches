package com.marketplace.entity;

import lombok.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PermissionUtilisateurId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer personne;
    private Integer permission;

    public Integer getPersonne() { return personne; }
    public void setPersonne(Integer personne) { this.personne = personne; }
    public Integer getPermission() { return permission; }
    public void setPermission(Integer permission) { this.permission = permission; }
}

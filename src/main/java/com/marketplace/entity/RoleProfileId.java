package com.marketplace.entity;

import lombok.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoleProfileId implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String profile;
    private Integer permission;

    // Getters and Setters (generated for Eclipse compatibility)
    public String getProfile() { return profile; }
    public void setProfile(String profile) { this.profile = profile; }
    public Integer getPermission() { return permission; }
    public void setPermission(Integer permission) { this.permission = permission; }
}

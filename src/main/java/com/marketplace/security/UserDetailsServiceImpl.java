package com.marketplace.security;

import com.marketplace.entity.Personne;
import com.marketplace.entity.PermissionUtilisateur;
import com.marketplace.entity.RoleProfile;
import com.marketplace.entity.RoleUtilisateur;
import com.marketplace.repository.PermissionUtilisateurRepository;
import com.marketplace.repository.PersonneRepository;
import com.marketplace.repository.RoleProfileRepository;
import com.marketplace.repository.RoleUtilisateurRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final PersonneRepository personneRepository;
    private final RoleUtilisateurRepository roleUtilisateurRepository;
    private final PermissionUtilisateurRepository permissionUtilisateurRepository;
    private final RoleProfileRepository roleProfileRepository;

    public UserDetailsServiceImpl(PersonneRepository personneRepository,
            RoleUtilisateurRepository roleUtilisateurRepository,
            PermissionUtilisateurRepository permissionUtilisateurRepository,
            RoleProfileRepository roleProfileRepository) {
        this.personneRepository = personneRepository;
        this.roleUtilisateurRepository = roleUtilisateurRepository;
        this.permissionUtilisateurRepository = permissionUtilisateurRepository;
        this.roleProfileRepository = roleProfileRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Personne personne = personneRepository.findByLogin(username)
            .or(() -> personneRepository.findByEmail(username))
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + username));
        
        if (!"ACTIF".equals(personne.getEtat())) {
            throw new UsernameNotFoundException("Compte inactif");
        }
        
        return new User(
            personne.getLogin(),
            personne.getMotpasse(),
            personne.getEtat().equals("ACTIF"),
            true,
            true,
            true,
            getAuthorities(personne.getIdpersonne())
        );
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(Integer idpersonne) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Set<String> profileIds = new LinkedHashSet<>();

        // 1. Profile-based roles (from RoleUtilisateur)
        List<RoleUtilisateur> roles = roleUtilisateurRepository.findByPersonneIdpersonneAndEtat(idpersonne, "ACTIF");

        for (RoleUtilisateur role : roles) {
            String profileId = role.getProfile().getIdprofile();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + profileId));
            profileIds.add(profileId);
        }

        // 2. Personne.role field (fallback)
        Personne personne = personneRepository.findById(idpersonne).orElse(null);
        if (personne != null && personne.getRole() != null && !personne.getRole().isBlank()) {
            String rid = personne.getRole().trim().toUpperCase();
            SimpleGrantedAuthority extra = new SimpleGrantedAuthority("ROLE_" + rid);
            boolean already = authorities.stream().anyMatch(a -> a.getAuthority().equals(extra.getAuthority()));
            if (!already) {
                authorities.add(extra);
            }
            profileIds.add(rid);
        }

        // 3. Permissions inherited from the user's profile(s) — a permission granted to a
        // profile applies to every user holding that profile (via RoleProfile).
        for (String profileId : profileIds) {
            List<RoleProfile> profilePerms = roleProfileRepository.findByProfileIdprofileAndEtat(profileId, "ACTIF");
            for (RoleProfile rp : profilePerms) {
                addPermissionAuthority(authorities, rp.getPermission() != null ? rp.getPermission().getIdpermission() : null);
            }
        }

        // 4. Direct user permissions (from PermissionUtilisateur) — override/complement the profile ones
        List<PermissionUtilisateur> directPerms = permissionUtilisateurRepository
                .findByPersonneIdpersonneAndEtat(idpersonne, "ACTIF");
        for (PermissionUtilisateur pu : directPerms) {
            addPermissionAuthority(authorities, pu.getPermission() != null ? pu.getPermission().getIdpermission() : null);
        }

        return authorities;
    }

    private void addPermissionAuthority(List<GrantedAuthority> authorities, Integer idpermission) {
        if (idpermission == null) {
            return;
        }
        String permAuth = "PERM_" + idpermission;
        boolean alreadyPerm = authorities.stream().anyMatch(a -> a.getAuthority().equals(permAuth));
        if (!alreadyPerm) {
            authorities.add(new SimpleGrantedAuthority(permAuth));
        }
    }
}

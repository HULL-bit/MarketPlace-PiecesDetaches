package com.marketplace.repository;

import com.marketplace.entity.RoleUtilisateur;
import com.marketplace.entity.RoleUtilisateurId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleUtilisateurRepository extends JpaRepository<RoleUtilisateur, RoleUtilisateurId> {
    
    List<RoleUtilisateur> findByPersonneIdpersonne(Integer idpersonne);
    
    List<RoleUtilisateur> findByProfileIdprofile(String idprofile);
    
    List<RoleUtilisateur> findByPersonneIdpersonneAndEtat(Integer idpersonne, String etat);

    void deleteByPersonne_Idpersonne(Integer idpersonne);
}

package com.marketplace.repository;

import com.marketplace.entity.PermissionUtilisateur;
import com.marketplace.entity.PermissionUtilisateurId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionUtilisateurRepository extends JpaRepository<PermissionUtilisateur, PermissionUtilisateurId> {

    List<PermissionUtilisateur> findByPersonneIdpersonne(Integer idpersonne);

    List<PermissionUtilisateur> findByPersonneIdpersonneAndEtat(Integer idpersonne, String etat);

    void deleteByPersonne_Idpersonne(Integer idpersonne);
}

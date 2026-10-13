package com.marketplace.repository;

import com.marketplace.entity.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends GenericRepository<Profile, String> {
    
    Optional<Profile> findByLibelle(String libelle);
    
    Optional<Profile> findByEtat(String etat);
}

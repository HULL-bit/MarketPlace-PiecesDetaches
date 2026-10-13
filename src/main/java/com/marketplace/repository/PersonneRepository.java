package com.marketplace.repository;

import com.marketplace.entity.Personne;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonneRepository extends GenericRepository<Personne, Integer> {
    
    Optional<Personne> findByEmail(String email);
    
    Optional<Personne> findByLogin(String login);
    
    boolean existsByEmail(String email);
    
    boolean existsByLogin(String login);
    
    Optional<Personne> findByTel(Long tel);
}

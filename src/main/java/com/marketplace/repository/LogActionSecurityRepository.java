package com.marketplace.repository;

import com.marketplace.entity.LogActionSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogActionSecurityRepository extends JpaRepository<LogActionSecurity, Integer> {
    
    List<LogActionSecurity> findByPersonneIdpersonne(Integer idpersonne);
    
    List<LogActionSecurity> findByTablebd(String tablebd);
    
    List<LogActionSecurity> findByActionContainingIgnoreCase(String action);
}

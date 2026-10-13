package com.marketplace.util;

import com.marketplace.entity.LogActionSecurity;
import com.marketplace.entity.Personne;
import com.marketplace.repository.LogActionSecurityRepository;
import com.marketplace.repository.PersonneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SecurityLogger {

    private static final Logger log = LoggerFactory.getLogger(SecurityLogger.class);
    
    private final LogActionSecurityRepository logRepository;
    private final PersonneRepository personneRepository;

    public SecurityLogger(LogActionSecurityRepository logRepository, PersonneRepository personneRepository) {
        this.logRepository = logRepository;
        this.personneRepository = personneRepository;
    }
    
    public void logAction(String action, String table, String details) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth != null ? auth.getName() : "SYSTEM";
            
            LogActionSecurity logEntry = new LogActionSecurity();
            logEntry.setAction(action + " sur " + table + ": " + details);
            logEntry.setDate(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
            logEntry.setEtat("SUCCESS");
            logEntry.setTablebd(table);
            
            resolvePersonne(username).ifPresent(logEntry::setPersonne);
            
            logRepository.save(logEntry);
            
            log.info("ACTION SECURITY [{}] - Table: {} - Details: {} - By: {}", action, table, details, username);
        } catch (Exception e) {
            log.error("Erreur logging action securite", e);
        }
    }
    
    public void logError(String action, String table, String details, Exception ex) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth != null ? auth.getName() : "SYSTEM";
            
            LogActionSecurity logEntry = new LogActionSecurity();
            logEntry.setAction("ERROR: " + action + " sur " + table + ": " + details);
            logEntry.setDate(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
            logEntry.setEtat("ERROR");
            logEntry.setTablebd(table);
            
            resolvePersonne(username).ifPresent(logEntry::setPersonne);
            
            logRepository.save(logEntry);
            
            log.error("ACTION SECURITY ERROR [{}] - Table: {} - Details: {} - By: {} - Error: {}", 
                action, table, details, username, ex.getMessage());
        } catch (Exception e) {
            log.error("Erreur logging action securite", e);
        }
    }

    private java.util.Optional<Personne> resolvePersonne(String login) {
        if (login == null || "anonymousUser".equals(login)) {
            return java.util.Optional.empty();
        }
        return personneRepository.findByLogin(login);
    }
}

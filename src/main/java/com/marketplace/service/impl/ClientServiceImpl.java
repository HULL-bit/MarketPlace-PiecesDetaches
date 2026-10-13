package com.marketplace.service.impl;

import com.marketplace.entity.Client;
import com.marketplace.repository.ClientRepository;
import com.marketplace.service.ClientService;
import com.marketplace.util.SecurityLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    
    private final ClientRepository clientRepository;
    private final SecurityLogger securityLogger;

    public ClientServiceImpl(ClientRepository clientRepository, SecurityLogger securityLogger) {
        this.clientRepository = clientRepository;
        this.securityLogger = securityLogger;
    }

    
    @Override
    public Client save(Client client) {
        Client saved = clientRepository.save(client);
        securityLogger.logAction("CREATE", "client", saved.getIdclient().toString());
        return saved;
    }
    
    @Override
    public Client update(Client client) {
        Client updated = clientRepository.save(client);
        securityLogger.logAction("UPDATE", "client", updated.getIdclient().toString());
        return updated;
    }
    
    @Override
    public void delete(Integer id) {
        clientRepository.deleteById(id);
        securityLogger.logAction("DELETE", "client", id.toString());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findById(Integer id) {
        return clientRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findByPersonneId(Integer idpersonne) {
        return clientRepository.findByPersonneIdpersonne(idpersonne);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Client> findByNomContaining(String nom) {
        return clientRepository.findByPersonneNomContainingIgnoreCase(nom);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.existsByPersonneEmail(email);
    }
}
